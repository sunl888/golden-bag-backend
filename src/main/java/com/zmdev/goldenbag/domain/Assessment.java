package com.zmdev.goldenbag.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 考核記錄
 */
@Entity
public class Assessment {
    @CreatedDate
    private Date createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @LastModifiedDate
    private Date updatedAt;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "assessment")
    private List<AssessmentProjectScore> assessmentProjectScores;

    @OneToMany(mappedBy = "assessment")
    private List<AssessmentInputContent> assessmentInputContents;

    @ManyToOne
    private AssessmentTemplate assessmentTemplate;
    // 間接經理審核意見
    private String indirectManagerAuditComments;
    // 直接經理評價
    private String directManagerEvaluation;
    // 职级系数
    private Double rankCoefficient;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AssessmentProjectScore> getAssessmentProjectScores() {
        return assessmentProjectScores;
    }

    public void setAssessmentProjectScores(List<AssessmentProjectScore> assessmentProjectScores) {
        this.assessmentProjectScores = assessmentProjectScores;
    }

    public List<AssessmentInputContent> getAssessmentInputContents() {
        return assessmentInputContents;
    }

    public void setAssessmentInputContents(List<AssessmentInputContent> assessmentInputContents) {
        this.assessmentInputContents = assessmentInputContents;
    }

    public String getDirectManagerEvaluation() {
        return directManagerEvaluation;
    }

    public void setDirectManagerEvaluation(String directManagerEvaluation) {
        this.directManagerEvaluation = directManagerEvaluation;
    }

    public AssessmentTemplate getAssessmentTemplate() {
        return assessmentTemplate;
    }

    public void setAssessmentTemplate(AssessmentTemplate assessmentTemplate) {
        this.assessmentTemplate = assessmentTemplate;
    }

    public String getIndirectManagerAuditComments() {
        return indirectManagerAuditComments;
    }

    public void setIndirectManagerAuditComments(String indirectManagerAuditComments) {
        this.indirectManagerAuditComments = indirectManagerAuditComments;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getRankCoefficient() {
        return rankCoefficient;
    }

    public void setRankCoefficient(Double rankCoefficient) {
        this.rankCoefficient = rankCoefficient;
    }

    public enum Status {
        SUBMITTED, // 已提交
        DIRECT_MANAGER_EVALUATED, //直接經理已經評價
        INDIRECT_MANAGER_RECHECK, // 間接經理已經複核
        FINISHED // 已完成
    }
}
