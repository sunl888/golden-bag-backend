package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.Assessment;
import com.zmdev.goldenbag.domain.Quarter;
import com.zmdev.goldenbag.domain.User;

public interface AssessmentService extends BaseService<Assessment, Long> {

    void storeAssessment(Assessment assessment, User creator);

    void directManagerScore(Assessment assessment, Long assessmentId, User operater);

    boolean isSubmitedWithCurrentQuarter(User user, Quarter quarter);
}
