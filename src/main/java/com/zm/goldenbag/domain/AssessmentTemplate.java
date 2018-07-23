package com.zm.goldenbag.domain;

import javax.persistence.*;
import java.util.Set;


@Entity
public class AssessmentTemplate {

    public enum Type {
        STAFF_TEMPLATE,
        MANAGER_TEMPLATE;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "assessmentTemplate")
    private Set<AssessmentProject> assessmentProjects;

    @OneToMany(mappedBy = "assessmentTemplate")
    private Set<AssessmentInput> assessmentInputs;

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

    public Set<AssessmentProject> getAssessmentProjects() {
        return assessmentProjects;
    }

    public void setAssessmentProjects(Set<AssessmentProject> assessmentProjects) {
        this.assessmentProjects = assessmentProjects;
    }

    public Set<AssessmentInput> getAssessmentInputs() {
        return assessmentInputs;
    }

    public void setAssessmentInputs(Set<AssessmentInput> assessmentInputs) {
        this.assessmentInputs = assessmentInputs;
    }
}
