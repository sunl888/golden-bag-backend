package com.zmdev.goldenbag.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * 考核表中的考核標準的子項
 */
@Entity
public class AssessmentProjectItem {
    @Id
    @GeneratedValue
    private Long id;

    private int score;

    private String title;

    @ManyToOne
    @JsonBackReference
    @PrimaryKeyJoinColumn(name = "assessment_project_id")
    private AssessmentProject assessmentProject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AssessmentProject getAssessmentProject() {
        return assessmentProject;
    }

    public void setAssessmentProject(AssessmentProject assessmentProject) {
        this.assessmentProject = assessmentProject;
    }
}
