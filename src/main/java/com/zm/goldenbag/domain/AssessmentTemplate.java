package com.zm.goldenbag.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class AssessmentTemplate {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany
    private Set<AssessmentProject> assessmentProjects;

    @OneToMany
    private Set<AssessmentInput> assessmentInputs;
}
