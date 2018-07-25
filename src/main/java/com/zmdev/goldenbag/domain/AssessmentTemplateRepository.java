package com.zmdev.goldenbag.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssessmentTemplateRepository extends JpaRepository<AssessmentTemplate, Long> {
    List<AssessmentTemplate> findByType(AssessmentTemplate.Type type);
}
