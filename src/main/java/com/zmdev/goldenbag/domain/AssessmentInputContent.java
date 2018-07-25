package com.zmdev.goldenbag.domain;


import javax.persistence.*;

/**
 * 考核表中的工作总结等輸入框輸入的內容 和 考核記錄 對應
 */
@Entity
public class AssessmentInputContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "assessment_id")
    private Assessment assessment;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "assessment_project_id")
    private AssessmentProject assessmentProject;

    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
