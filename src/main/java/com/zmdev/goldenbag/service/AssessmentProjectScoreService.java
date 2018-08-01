package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.Assessment;
import com.zmdev.goldenbag.domain.AssessmentProject;
import com.zmdev.goldenbag.domain.AssessmentProjectScore;

public interface AssessmentProjectScoreService extends BaseService<AssessmentProjectScore, Long> {
    AssessmentProjectScore findByAssessmentProjectAndAssessment(AssessmentProject assessmentProject, Assessment assessment);

}
