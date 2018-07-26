package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.Assessment;
import com.zmdev.goldenbag.domain.AssessmentProject;
import com.zmdev.goldenbag.domain.AssessmentProjectScore;
import com.zmdev.goldenbag.domain.AssessmentProjectScoreRepository;
import com.zmdev.goldenbag.service.AssessmentProjectScoreService;
import org.springframework.stereotype.Service;

@Service
public class AssessmentProjectScoreServiceImpl extends BaseServiceImpl<AssessmentProjectScore, Long, AssessmentProjectScoreRepository> implements AssessmentProjectScoreService {

    public AssessmentProjectScore findByAssessmentProjectAndAssessment(AssessmentProject assessmentProject, Assessment assessment) {
        return repository.findByAssessmentProjectAndAssessment(assessmentProject, assessment);
    }
}
