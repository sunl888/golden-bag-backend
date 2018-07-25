package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.Assessment;
import com.zmdev.goldenbag.domain.User;

public interface AssessmentService extends BaseService<Assessment, Long> {
    void storeAssessment(Assessment assessment, User creator);
}
