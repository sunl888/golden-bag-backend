package com.zm.goldenbag.domain;

import javax.persistence.*;

/**
 * 考核表中的工作总结等輸入框
 */
@Entity
public class AssessmentInput {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "assessment_template_id")
    private AssessmentTemplate assessmentTemplate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AssessmentTemplate getAssessmentTemplate() {
        return assessmentTemplate;
    }

    public void setAssessmentTemplate(AssessmentTemplate assessmentTemplate) {
        this.assessmentTemplate = assessmentTemplate;
    }
}
