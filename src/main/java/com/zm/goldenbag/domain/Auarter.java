package com.zm.goldenbag.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Auarter {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Date startDate;

    private Date startAssessmentDate;

    @OneToMany
    private Set<AssessmentTemplate> assessmentTemplates;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartAssessmentDate() {
        return startAssessmentDate;
    }

    public void setStartAssessmentDate(Date startAssessmentDate) {
        this.startAssessmentDate = startAssessmentDate;
    }

    public Set<AssessmentTemplate> getAssessmentTemplates() {
        return assessmentTemplates;
    }

    public void setAssessmentTemplates(Set<AssessmentTemplate> assessmentTemplates) {
        this.assessmentTemplates = assessmentTemplates;
    }
}
