package com.zmdev.goldenbag.web.assessment;


import com.zmdev.goldenbag.domain.Assessment;
import com.zmdev.goldenbag.service.AssessmentService;
import com.zmdev.goldenbag.web.Auth;
import com.zmdev.goldenbag.web.BaseController;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/self_evaluation", produces = "application/json;charset=UTF-8")
public class SelfEvaluationController extends BaseController {

    private AssessmentService assessmentService;

    private Auth auth;

    @Autowired
    public SelfEvaluationController(AssessmentService assessmentService, Auth auth) {
        this.assessmentService = assessmentService;
        this.auth = auth;
    }

    // 用户提交考核记录(申请)
    @PostMapping
    public Result store(@RequestBody Assessment assessment) {
        assessmentService.storeAssessment(assessment, auth.getUser());
        return ResultGenerator.genSuccessResult();
    }
}
