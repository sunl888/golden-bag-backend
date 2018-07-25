package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.Assessment;
import com.zmdev.goldenbag.service.AssessmentService;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/assessments")
public class AssessmentController extends BaseController {

    @Autowired
    private AssessmentService assessmentService;

    @PostMapping
    public Result store(@RequestBody Assessment assessment) {

        assessmentService.storeAssessment(assessment, getUser());
        return ResultGenerator.genSuccessResult();
    }
}