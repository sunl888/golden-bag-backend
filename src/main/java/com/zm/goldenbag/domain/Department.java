package com.zm.goldenbag.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Department {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "parent_id", referencedColumnName = "id")
    private Department parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private Set<Department> projectGroups;

    public Department() {
    }

    public Department(String name, Department parent) {
        this.name = name;
        this.parent = parent;
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

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    public Set<Department> getProjectGroups() {
        return projectGroups;
    }

    public void setProjectGroups(Set<Department> projectGroups) {
        this.projectGroups = projectGroups;
    }

}
