package com.zmdev.goldenbag.web.assessment;


import com.zmdev.goldenbag.domain.Assessment;
import com.zmdev.goldenbag.domain.AssessmentTemplate;
import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.exception.ModelNotFoundException;
import com.zmdev.goldenbag.service.AssessmentService;
import com.zmdev.goldenbag.service.AssessmentTemplateService;
import com.zmdev.goldenbag.utils.ExportAssessment;
import com.zmdev.goldenbag.web.Auth;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/assessments", produces = "application/json;charset=UTF-8")
public class AssessmentController {

    private AssessmentService assessmentService;
    @Autowired
    private AssessmentTemplateService assessmentTemplateService;
    @Autowired
    private ExportAssessment exportAssessment;

    private Auth auth;

    @Autowired
    public AssessmentController(AssessmentService assessmentService, Auth auth) {
        this.assessmentService = assessmentService;
        this.auth = auth;
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
     * 导出考核记录
     *
     * @param assessmentId
     * @return Result
     * @throws IOException
     */
    @GetMapping("/export/{assessmentId}")
    public Result export(@PathVariable Long assessmentId) throws IOException {
        Assessment assessment = assessmentService.findById(assessmentId).orElseThrow(
                () -> new ModelNotFoundException("没有该考核记录")
        );
        AssessmentTemplate template = assessment.getAssessmentTemplate();
        if (template == null) {
            throw new ModelNotFoundException("模版不存在");
        }
        Workbook workbook = exportAssessment.createTemplate(assessment, template);
        FileOutputStream fileOut = new FileOutputStream("assessment.xls");
        workbook.write(fileOut);
        fileOut.close();
        return ResultGenerator.genSuccessResult();
    }
}
