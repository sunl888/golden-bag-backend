package com.zmdev.goldenbag.domain;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 員工表
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
    @Column(nullable = false)
    private String name;
    private String phone;
    private Date entryDate;
    @Enumerated
    private Gender gender;
    // 职级系数
    private Double rankCoefficient;
    // 角色（岗位）
    private String role;
    //
    @OneToOne
    @JoinColumn(name = "direct_manager_id")
    private User directManager;
    @OneToOne
    @JoinColumn(name = "indirect_manager_id")
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", name='" + name + '\'' +
                ", entryDate=" + entryDate +
                ", gender=" + gender +
                ", rankCoefficient=" + rankCoefficient +
                ", role='" + role + '\'' +
                ", directManager=" + directManager +
                ", indirectManager=" + indirectManager +
                ", department=" + department +
                '}';
    }

    public enum Gender {
        Man,
        WOMAN
    }
}
