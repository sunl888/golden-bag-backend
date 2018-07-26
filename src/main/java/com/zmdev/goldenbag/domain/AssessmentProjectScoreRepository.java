package com.zmdev.goldenbag.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssessmentProjectScoreRepository extends JpaRepository<AssessmentProjectScore, Long> {

    @Query("select a from AssessmentProjectScore a where a.assessmentProject=:assessmentProject and a.assessment=:assessment")
    AssessmentProjectScore findByAssessmentProjectAndAssessment(@Param("assessmentProject") AssessmentProject assessmentProject, @Param("assessment") Assessment assessment);
}
