package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.Permission;
import com.zmdev.goldenbag.domain.Role;
import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.service.PermissionService;
import com.zmdev.goldenbag.service.RoleService;
import com.zmdev.goldenbag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping(value = "/test", produces = "application/json;charset=UTF-8")
public class IndexController extends BaseController {

    private PermissionService permissionService;

    private RoleService roleService;

    private UserService userService;

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private void storePermission(String topModuleString, String moduleName, BasePermission[] basePermissions) {
        for (BasePermission basePermission : basePermissions) {
            Permission p = new Permission();
            p.setName(topModuleString + "." + moduleName + "." + basePermission.getActionName());
            p.setDisplayName(basePermission.getDisplayName());
            p.setDescription(basePermission.getDisplayName() + Permission.getModules().get(moduleName).substring(0, 2));
            p.setCreatedAt(new Date());
            p.setUpdatedAt(new Date());
            permissionService.save(p);
        }
    }

    private void setupPermission() {
        Map<String, String[]> resources = new HashMap<>();
        resources.put("basic", new String[]{"department", "user", "role"});
        for (Map.Entry<String, String[]> entry : resources.entrySet()) {
            for (String moduleName : entry.getValue()) {
                storePermission(entry.getKey(), moduleName, basePermissions);
            }
        }

        Permission p = new Permission();
        p.setName("basic.role.permission");
        p.setDisplayName("查看指定角色的权限");
        p.setDescription("查看指定角色的权限");
        p.setCreatedAt(new Date());
        p.setUpdatedAt(new Date());
        permissionService.save(p);

        storePermission("basic", "quarter",
                new BasePermission[]{new BasePermission("view", "查看"), new BasePermission("add", "添加")});
        storePermission("basic", "permission", new BasePermission[]{new BasePermission("view", "查看")});

        p = new Permission();
        p.setName("basic.permission.allMenus");
        p.setDisplayName("获取菜单");
        p.setDescription("获取菜单");
        p.setCreatedAt(new Date());
        p.setUpdatedAt(new Date());
        permissionService.save(p);

        storePermission("template_module", "template_module", new BasePermission[]{
                new BasePermission("view", "查看"),
                new BasePermission("add", "添加"),
                new BasePermission("edit", "编辑"),
        });

        p = new Permission();
        p.setName("template_module.template_module.export");
        p.setDisplayName("导出");
        p.setDescription("导出模板");
        p.setCreatedAt(new Date());
        p.setUpdatedAt(new Date());
        permissionService.save(p);

        p = new Permission();
        p.setName("assessment.directManagerScore.add");
        p.setDisplayName("直接经理评分");
        p.setDescription("直接经理评分");
        p.setCreatedAt(new Date());
        p.setUpdatedAt(new Date());
        permissionService.save(p);

        p = new Permission();
        p.setName("assessment.indirectManagerAuditComments.add");
        p.setDisplayName("间接经理建议");
        p.setDescription("间接经理建议");
        p.setCreatedAt(new Date());
        p.setUpdatedAt(new Date());
        permissionService.save(p);

        p = new Permission();
        p.setName("assessment.selfEvaluation.add");
        p.setDisplayName("员工自评");
        p.setDescription("员工自评");
        p.setCreatedAt(new Date());
        p.setUpdatedAt(new Date());
        permissionService.save(p);
    }

    private void setupRole() {
        Role adminRole = new Role();
        adminRole.setName("admin");
        adminRole.setDisplayName("管理员");
        adminRole.setDescription("管理员拥有所有权限");
        adminRole.setPermissions(permissionService.findAll());
        roleService.save(adminRole);

        Role directManagerRole = new Role();
        directManagerRole.setName("direct_manager");
        directManagerRole.setDisplayName("直接经理");
        directManagerRole.setDescription("直接经理可以给员工评分");
        List<Permission> permissions = new ArrayList<>();
        permissions.add(permissionService.findByName("assessment.directManagerScore.add"));
        directManagerRole.setPermissions(permissions);
        roleService.save(directManagerRole);

        Role indirectManagerRole = new Role();
        indirectManagerRole.setName("indirect_manager");
        indirectManagerRole.setDisplayName("间接经理");
        indirectManagerRole.setDescription("间接经理可以给员工建议");
        permissions = new ArrayList<>();
        permissions.add(permissionService.findByName("assessment.indirectManagerAuditComments.add"));
        indirectManagerRole.setPermissions(permissions);
        roleService.save(indirectManagerRole);

        Role employeeRole = new Role();
        employeeRole.setName("employee");
        employeeRole.setDisplayName("员工");
        employeeRole.setDescription("员工");
        permissions = new ArrayList<>();
        permissions.add(permissionService.findByName("assessment.selfEvaluation.add"));
        employeeRole.setPermissions(permissions);
        roleService.save(employeeRole);
    }

    public void setupUser() {
        User user = new User();
        user.setId(9L);
        user.setPhone("13956460801");
        user.setName("taoyu");
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findByName("admin"));
        user.setRoles(roles);
        userService.save(user);
    }

    @GetMapping("setup")
    public String setup() {
        setupPermission();
        setupRole();
        setupUser();
        return "ok";
    }


    private BasePermission[] basePermissions = {
            new BasePermission("view", "查看"),
            new BasePermission("add", "添加"),
            new BasePermission("edit", "编辑"),
            new BasePermission("delete", "删除"),
    };

    private class BasePermission {
        private String actionName;
        private String displayName;

        public BasePermission(String actionName, String displayName) {
            this.actionName = actionName;
            this.displayName = displayName;
        }

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
    }
}
