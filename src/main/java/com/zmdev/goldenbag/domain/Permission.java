package com.zmdev.goldenbag.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Permission implements Comparable<Permission> {
    @Transient
    private static Map<String, String> modules = new HashMap<>();

    static {
        modules.put("basic", "基础模块");
        modules.put("template_module", "模板模块");
        modules.put("template", "模板管理");
        modules.put("user", "用户管理");
        modules.put("department", "部门管理");
        modules.put("assessment", "考核模块");
        modules.put("quarter", "季度管理");
        modules.put("permission", "权限管理");
        modules.put("role", "角色管理");
        modules.put("selfEvaluation", "员工自评");
        modules.put("indirectManagerAuditComments", "间接经理建议");
        modules.put("directManagerScore", "直接经理评分");
    }

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String displayName;
    private String description;

    @Column(nullable = false)
    @JsonIgnore
    private Boolean menuable;
    // handlerMethod.getShortLogMessage();
    // @Column(nullable = false, unique = true)
    // private String shortLogMessage;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    public static Map<String, String> getModules() {
        return modules;
    }

    public static void setModules(Map<String, String> modules) {
        Permission.modules = modules;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getMenuable() {
        return menuable;
    }

    public void setMenuable(Boolean menuable) {
        this.menuable = menuable;
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

//    public String getShortLogMessage() {
//        return shortLogMessage;
//    }
//
//    public void setShortLogMessage(String shortLogMessage) {
//        this.shortLogMessage = shortLogMessage;
//    }

    /**
     * 获取顶级模块
     * 如果 permission.name=basic.user.show 那么调用此方法返回的是basic
     *
     * @return 顶级模块
     */
    public String getTopModuleName() {
        if (this.getName() == null) {
            return "";
        }
        int index = this.getName().indexOf('.');
        if (index > 0)
            return this.getName().substring(0, index);
        return "";
    }

    /**
     * 获取第二级模块
     * 如果 permission.name=basic.user.show 那么调用此方法返回的是user
     *
     * @return 第二级模块
     */
    public String getModuleName() {
        String name = getName();
        if (name == null) {
            return "";
        }
        int first = name.indexOf('.') + 1;
        int sec = name.indexOf('.', first);

        return name.substring(first, sec);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public int compareTo(Permission permission) {
        if (permission == null) {
            return 1;
        }
        if (permission.getCreatedAt() == null && getCreatedAt() == null) {
            return 0;
        }
        if (permission.getCreatedAt() == null) {
            return 1;
        }
        if (getCreatedAt() == null) {
            return -1;
        }

        return permission.getCreatedAt().compareTo(getCreatedAt());
    }
}
