package com.zmdev.goldenbag.domain;


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
public class Permission {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String displayName;

    private String description;

    // handlerMethod.getShortLogMessage();
    @Column(nullable = false, unique = true)
    private String shortLogMessage;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @Transient
    private static Map<String, String> modules = new HashMap<>();

    static {
        modules.put("basic", "基础模块");
        modules.put("template", "模板管理");
        modules.put("user", "用户管理");
        modules.put("department", "部门管理");
        modules.put("assessment", "考核模块");
        modules.put("self_evaluation", "员工自评");
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

    public static Map<String, String> getModules() {
        return modules;
    }

    public static void setModules(Map<String, String> modules) {
        Permission.modules = modules;
    }

    public String getShortLogMessage() {
        return shortLogMessage;
    }

    public void setShortLogMessage(String shortLogMessage) {
        this.shortLogMessage = shortLogMessage;
    }

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
}
