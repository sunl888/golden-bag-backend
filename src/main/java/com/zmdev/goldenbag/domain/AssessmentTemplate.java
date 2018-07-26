package com.zmdev.goldenbag.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 考核模板
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AssessmentTemplate {

    @CreatedDate
    private Date createdAt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @LastModifiedDate
    private Date updatedAt;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "quarter_id")
    private Quarter quarter;

    private String name;

    @Enumerated
    private Type type;

    @OneToMany(mappedBy = "assessmentTemplate")
    private List<AssessmentProject> assessmentProjects;

    @OneToMany(mappedBy = "assessmentTemplate")
    private List<AssessmentInput> assessmentInputs;

    public Quarter getQuarter() {
        return quarter;
    }

    public void setQuarter(Quarter quarter) {
        this.quarter = quarter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<AssessmentProject> getAssessmentProjects() {
        return assessmentProjects;
    }

    public void setAssessmentProjects(List<AssessmentProject> assessmentProjects) {
        this.assessmentProjects = assessmentProjects;
    }

    public List<AssessmentInput> getAssessmentInputs() {
        return assessmentInputs;
    }

    public void setAssessmentInputs(List<AssessmentInput> assessmentInputs) {
        this.assessmentInputs = assessmentInputs;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public enum Type {
        STAFF_TEMPLATE, // 員工模板
        MANAGER_TEMPLATE // 經理模板
    }
}
