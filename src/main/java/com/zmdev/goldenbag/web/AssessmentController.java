package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.Assessment;
import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.exception.AuthorizationException;
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

    // 用户提交考核记录(申请)
    @PostMapping
    public Result store(@RequestBody Assessment assessment) {
        assessmentService.storeAssessment(assessment, getUser());
        return ResultGenerator.genSuccessResult();
    }

    // 直接经理评分
    @RequestMapping(value = "/direct_manager_score/{assessmentId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result directManagerScore(@RequestBody Assessment assessment, @PathVariable Long assessmentId) {
        User auth = getUser();

        if (auth == null) {
            throw new AuthorizationException("请登陆");
        }
        assessmentService.directManagerScore(assessment, assessmentId, auth);
        return ResultGenerator.genSuccessResult();
    }

    // 间接经理提出建议
    @RequestMapping(value = "/indirect_manager_comments/{assessmentId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result indirectManagerAuditComments(@RequestBody Assessment assessment, @PathVariable Long assessmentId) {
        User auth = getUser();

        if (auth == null) {
            throw new AuthorizationException("请登陆");
        }
        assessmentService.indirectManagerAuditComments(assessment, assessmentId, auth);
        return ResultGenerator.genSuccessResult();
    }

}