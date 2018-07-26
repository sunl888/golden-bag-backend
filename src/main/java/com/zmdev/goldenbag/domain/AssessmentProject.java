package com.zmdev.goldenbag.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/**
 * 考核表中的考核標準
 */

@Entity
public class AssessmentProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JsonBackReference
    @PrimaryKeyJoinColumn(name = "assessment_template_id")
    private AssessmentTemplate assessmentTemplate;

    @OneToMany(mappedBy = "assessmentProject")
    private List<AssessmentProjectItem> items;

    private int sort;

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

    public List<AssessmentProjectItem> getItems() {
        return items;
    }

    public void setItems(List<AssessmentProjectItem> items) {
        this.items = items;
    }

    public AssessmentTemplate getAssessmentTemplate() {
        return assessmentTemplate;
    }

    public void setAssessmentTemplate(AssessmentTemplate assessmentTemplate) {
        this.assessmentTemplate = assessmentTemplate;
    }
}


