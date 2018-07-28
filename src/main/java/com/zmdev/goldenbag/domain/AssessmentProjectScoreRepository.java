package com.zmdev.goldenbag.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AssessmentProjectScoreRepository extends JpaRepository<AssessmentProjectScore, Long> {

    AssessmentProjectScore findByAssessmentProjectAndAssessment(@Param("assessmentProject") AssessmentProject assessmentProject, @Param("assessment") Assessment assessment);
}
