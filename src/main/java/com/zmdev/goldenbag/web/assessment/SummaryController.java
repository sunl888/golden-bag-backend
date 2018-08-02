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
import com.zmdev.goldenbag.web.insterceptor.PermissionInterceptor;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

// 考核汇总
@RestController
@RequestMapping(value = "/assessments", produces = "application/json;charset=UTF-8")
public class SummaryController {

    private final AssessmentService assessmentService;
    private final AssessmentTemplateService assessmentTemplateService;
    private final ExportAssessment exportAssessment;
    private final QuarterService quarterService;
    private Auth auth;

    @Autowired
    public SummaryController(AssessmentService assessmentService, Auth auth, AssessmentTemplateService assessmentTemplateService, ExportAssessment exportAssessment, QuarterService quarterService) {
        this.assessmentService = assessmentService;
        this.auth = auth;
        this.assessmentTemplateService = assessmentTemplateService;
        this.exportAssessment = exportAssessment;
        this.quarterService = quarterService;
    }

    static {
        PermissionInterceptor.addSpecialAbilitie(SummaryController.class, "show", "show");
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
     * 根据条件显示指定的考核记录列表
     *
     * @param quarter_id
     * @param page
     * @param size
     * @return Result
     */
    @GetMapping()
    public Result index(
            @RequestParam(value = "quarter_id", defaultValue = "0") Long quarter_id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Assessment> assessmentPage;
        if (quarter_id != 0) {
            Quarter quarter = quarterService.findById(quarter_id).orElseThrow(() ->
                    new ModelNotFoundException("该季度不存在")
            );
            assessmentPage = assessmentService.findByQuarterAndStatusIs(quarter, Assessment.Status.FINISHED,
                    PageRequest.of(page, size,
                            new Sort(Sort.Direction.DESC, "createdAt")
                    )
            );
        } else {
            Quarter quarter = quarterService.findCurrentQuarter();
            if (quarter == null) {
                throw new ModelNotFoundException("没有设置当前季度");
            }
            System.out.println(quarter.getId());
            assessmentPage = assessmentService.findByQuarterAndStatusIs(quarter, Assessment.Status.FINISHED,
                    PageRequest.of(page, size,
                            new Sort(Sort.Direction.DESC, "createdAt")
                    )
            );
        }
        return ResultGenerator.genSuccessResult(assessmentPage);
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

    /**
     * 按季度导出数据
     * example:
     * http://localhost:8080/assessments/batch_export/byquarters?quarer_ids=1&quarer_ids=2
     *
     * @param quarer_ids
     * @param response
     */
    @GetMapping("/batch_export/byquarters")
    public void batchExportByQuarterIds(@RequestParam() Long[] quarer_ids, HttpServletResponse response) throws Exception {
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
            this.batchExport(assessments, zipOutputStream);
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

    /**
     * 通过考核记录IDs导出指定的记录
     * example:
     * http://localhost:8080/assessments/batch_export/byassessments?assessment_ids=1&assessment_ids=5
     *
     * @param assessment_ids
     * @param response
     * @throws Exception
     */
    @GetMapping("/batch_export/byassessments")
    public void batchExportByAssessmentIds(@RequestParam() Long[] assessment_ids, HttpServletResponse response) throws Exception {
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(UUID.randomUUID() + "-batch.zip", "UTF-8"));
        OutputStream os = response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(os);
        List<Assessment> assessments = assessmentService.findByIds(assessment_ids);
        this.batchExport(assessments, zipOutputStream);
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

    /**
     * 批量导出数据
     *
     * @param assessments
     * @param zipOutputStream
     * @throws Exception
     */
    private void batchExport(List<Assessment> assessments, ZipOutputStream zipOutputStream) throws Exception {

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

}
