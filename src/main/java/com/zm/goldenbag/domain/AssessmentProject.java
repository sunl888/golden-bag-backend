package com.zm.goldenbag.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AssessmentProject {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToMany
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Set<AssessmentProjectItem> items;

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

    public Set<AssessmentProjectItem> getItems() {
        return items;
    }

    public void setItems(Set<AssessmentProjectItem> items) {
        this.items = items;
    }

}


