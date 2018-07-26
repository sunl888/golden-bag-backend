package com.zmdev.goldenbag.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssessmentTemplateRepository extends JpaRepository<AssessmentTemplate, Long> {
    List<AssessmentTemplate> findByType(AssessmentTemplate.Type type);

    @Query("select a from AssessmentTemplate a order by createdAt desc")
    AssessmentTemplate getLast();
}
