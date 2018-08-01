package com.zmdev.goldenbag.web.assessment;


import com.zmdev.goldenbag.domain.Assessment;
import com.zmdev.goldenbag.domain.AssessmentTemplate;
import com.zmdev.goldenbag.domain.Quarter;
import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.exception.ModelNotFoundException;
import com.zmdev.goldenbag.service.AssessmentService;
import com.zmdev.goldenbag.service.AssessmentTemplateService;
import com.zmdev.goldenbag.service.QuarterService;
import com.zmdev.goldenbag.utils.ExportAssessment;
import com.zmdev.goldenbag.web.Auth;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping(value = "/assessments", produces = "application/json;charset=UTF-8")
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final AssessmentTemplateService assessmentTemplateService;
    private final ExportAssessment exportAssessment;
    private final QuarterService quarterService;
    private Auth auth;

    @Autowired
    public AssessmentController(AssessmentService assessmentService, Auth auth, AssessmentTemplateService assessmentTemplateService, ExportAssessment exportAssessment, QuarterService quarterService) {
        this.assessmentService = assessmentService;
        this.auth = auth;
        this.assessmentTemplateService = assessmentTemplateService;
        this.exportAssessment = exportAssessment;
        this.quarterService = quarterService;
    }

    /**
     * 显示指定的考核记录
     *
     * @param assessment_id
     * @return Result
     */
    @RequestMapping(value = "/{assessment_id}")
    public Result show(@PathVariable("assessment_id") Long assessment_id) {

        Optional<Assessment> assessment = assessmentService.findById(assessment_id);
        User user = assessment.orElseThrow(
                () -> new ModelNotFoundException("不存在该考核记录")
        ).getUser();
        // 只有自己、直接经理、间接经理可以查看
        if (user.equals(auth.getUser()) || user.getDirectManager().equals(auth.getUser()) || user.getIndirectManager().equals(auth.getUser()))
            return ResultGenerator.genSuccessResult(assessment.get());
        return ResultGenerator.genFailResult("不能偷看别人的考核记录哦--");
    }

    /**
     * 导出一条考核记录
     * example:
     * http://localhost:8080/assessments/export/1
     *
     * @param assessment_id
     */
    @GetMapping("/export/{assessment_id}")
    public void export(@PathVariable() Long assessment_id, HttpServletResponse response) throws IOException {
        Assessment assessment = assessmentService.findById(assessment_id).orElseThrow(
                () -> new ModelNotFoundException("考核记录不存在")
        );
        AssessmentTemplate template = assessment.getAssessmentTemplate();
        if (template == null) {
            throw new ModelNotFoundException("模版不存在");
        }
        Workbook workbook = exportAssessment.createTemplate(assessment);
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(assessment.getUser().getName() + ".xls", "UTF-8"));
        OutputStream os = response.getOutputStream();
        try {
            workbook.write(os);
        } catch (Throwable e) {
            throw new Error("导出异常");
        } finally {
            try {
                workbook.close();
                os.flush();
                os.close();
            } catch (Throwable e) {
                os = null;
                workbook = null;
            }
        }
    }

    private void testExport(List<Assessment> assessments, ZipOutputStream zipOutputStream) throws Exception {

        for (Assessment assessment : assessments) {
            AssessmentTemplate template = assessment.getAssessmentTemplate();
            if (template == null) {
                throw new ModelNotFoundException("模版不存在");
            }
            Workbook workbook = exportAssessment.createTemplate(assessment);
            String fileName = assessment.getQuarter().getName() + "-" + assessment.getUser().getName();
            try {
                ZipEntry entry = new ZipEntry(fileName + ".xls");
                zipOutputStream.putNextEntry(entry);
                workbook.write(zipOutputStream);
            } catch (Throwable e) {
                throw new Exception("导出异常");
            } finally {
                try {
                    workbook.close();
                } catch (Throwable e) {
                    workbook = null;
                }
            }
        }
        try {
            // 关闭输出流
            zipOutputStream.flush();
            zipOutputStream.close();
        } catch (Throwable e) {
            zipOutputStream = null;
        }

    }

    /**
     * 按季度导出数据
     * example:
     * http://localhost:8080/assessments/batch_export/byquarters?quarer_ids=1&quarer_ids=2
     *
     * @param quarer_ids
     * @param response
     */
    @GetMapping("/batch_export/byquarters")
    public void batchExport(@RequestParam() Long[] quarer_ids, HttpServletResponse response) throws Exception {
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(UUID.randomUUID() + "-batch.zip", "UTF-8"));
        OutputStream os = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(os);

        for (Long quarterId : quarer_ids) {
            Quarter quarter = quarterService.findById(quarterId).orElseThrow(
                    () -> new ModelNotFoundException("指定的季度不存在")
            );
            List<Assessment> assessments = assessmentService.findByQuarter(quarter);
            this.testExport(assessments, zipOutputStream);
            /*for (Assessment assessment : assessments) {
                AssessmentTemplate template = assessment.getAssessmentTemplate();
                if (template == null) {
                    throw new ModelNotFoundException("模版不存在");
                }
                Workbook workbook = exportAssessment.createTemplate(assessment);
                String fileName = assessment.getQuarter().getName() + "-" + assessment.getUser().getName();
                try {
                    ZipEntry entry = new ZipEntry(fileName + ".xls");
                    zipOutputStream.putNextEntry(entry);
                    workbook.write(zipOutputStream);
                } catch (Throwable e) {
                    throw new Exception("导出异常");
                } finally {
                    try {
                        workbook.close();
                    } catch (Throwable e) {
                        workbook = null;
                    }
                }
            }*/
        }
        try {
            // 关闭输出流
            zipOutputStream.flush();
            zipOutputStream.close();
            os.flush();
            os.close();
        } catch (Throwable e) {
            zipOutputStream = null;
            os = null;
        }
    }
}
