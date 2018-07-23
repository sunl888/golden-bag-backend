package com.zmdev.goldenbag.domain;


import javax.persistence.*;

/**
 * 考核表中的每個項目的自評分數,直接經理評分,備註 和 考核記錄 對應
 */
@Entity
public class AssessmentProjectScore {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "assessment_id")
    private Assessment assessment;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "assessment_project_id")
    private AssessmentProject assessmentProject;

    private int selfScore;

    private int managerScore;

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

    public int getSelfScore() {
        return selfScore;
    }

    public void setSelfScore(int selfScore) {
        this.selfScore = selfScore;
    }

    public int getManagerScore() {
        return managerScore;
    }

    public void setManagerScore(int managerScore) {
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
