package com.zmdev.goldenbag.domain;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * 員工表
 */
@Entity
public class User {

    public enum Gender {
        Man,
        WOMAN
    }

    @Id
    private Long id;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @Column(nullable = false)
    private String name;

    private Date entryDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 职级系数
    private Double rankCoefficient;

    // 角色（岗位）
    private String role;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "direct_manager_id")
    private User directManager;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "indirect_manager_id")
    private User indirectManager;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "department_id")
    private Department department;

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

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Double getRankCoefficient() {
        return rankCoefficient;
    }

    public void setRankCoefficient(Double rankCoefficient) {
        this.rankCoefficient = rankCoefficient;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User getDirectManager() {
        return directManager;
    }

    public void setDirectManager(User directManager) {
        this.directManager = directManager;
    }

    public User getIndirectManager() {
        return indirectManager;
    }

    public void setIndirectManager(User indirectManager) {
        this.indirectManager = indirectManager;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
}
