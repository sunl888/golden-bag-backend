package com.zmdev.goldenbag.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * 考核表中的每個項目的自評分數,直接經理評分,備註 和 考核記錄 對應
 */
@Entity
public class AssessmentProjectScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference // 解決雙向引用導致的無限遞歸問題
    @PrimaryKeyJoinColumn(name = "assessment_id")
    private Assessment assessment;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "assessment_project_id")
    private AssessmentProject assessmentProject;

    @Column(nullable = true)
    private Integer selfScore;

    @Column(nullable = true)
    private Integer managerScore;

    private String remarks;


    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public AssessmentProject getAssessmentProject() {
        return assessmentProject;
    }

    public void setAssessmentProject(AssessmentProject assessmentProject) {
        this.assessmentProject = assessmentProject;
    }

    public Integer getSelfScore() {
        return selfScore;
    }

    public void setSelfScore(Integer selfScore) {
        this.selfScore = selfScore;
    }

    public Integer getManagerScore() {
        return managerScore;
    }

    public void setManagerScore(Integer managerScore) {
        this.managerScore = managerScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
