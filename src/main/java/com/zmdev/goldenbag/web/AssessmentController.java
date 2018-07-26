package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.Assessment;
import com.zmdev.goldenbag.service.AssessmentService;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/assessments")
public class AssessmentController extends BaseController {

    @Autowired
    private AssessmentService assessmentService;

    /**
     * 用户提交考核记录(申请)
     *
     * @param assessment
     * @return
     */
    @PostMapping
    public Result store(@RequestBody Assessment assessment) {
        assessmentService.storeAssessment(assessment, getUser());
        return ResultGenerator.genSuccessResult();
    }

    // 直接经理评分
    @RequestMapping(value = "/direct_manager_score/{assessmentId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result directManagerScore(@RequestBody Assessment assessment, @PathVariable Long assessmentId) {
        assessmentService.directManagerScore(assessment, assessmentId, getUser());
        return ResultGenerator.genSuccessResult();
    }
}