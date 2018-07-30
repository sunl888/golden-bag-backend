package com.zmdev.goldenbag.web.assessment;

import com.zmdev.goldenbag.domain.Assessment;
import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.exception.AuthorizationException;
import com.zmdev.goldenbag.service.AssessmentService;
import com.zmdev.goldenbag.web.Auth;
import com.zmdev.goldenbag.web.BaseController;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/direct_manager_score", produces = "application/json;charset=UTF-8")
public class DirectManagerScoreController extends BaseController {
    private AssessmentService assessmentService;

    private Auth auth;

    @Autowired
    public DirectManagerScoreController(AssessmentService assessmentService, Auth auth) {
        this.assessmentService = assessmentService;
        this.auth = auth;
    }

    // 直接经理评分
    @RequestMapping(value = "/{assessmentId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result store(@RequestBody Assessment assessment, @PathVariable Long assessmentId) {
        User user = auth.getUser();

        if (user == null) {
            throw new AuthorizationException("请登陆");
        }
        assessmentService.directManagerScore(assessment, assessmentId, user);
        return ResultGenerator.genSuccessResult();
    }
}
