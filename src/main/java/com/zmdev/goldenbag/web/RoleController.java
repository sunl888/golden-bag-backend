package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.Permission;
import com.zmdev.goldenbag.domain.Role;
import com.zmdev.goldenbag.service.PermissionService;
import com.zmdev.goldenbag.service.RoleService;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "roles", name = "roles")
public class RoleController {

    private RoleService roleService;
    private PermissionService permissionService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public Result index() {
        return ResultGenerator.genSuccessResult(roleService.findAll());
    }

    @GetMapping(value = "/{id}", name = "show")
    public Result show(@PathVariable Long id) {
        return ResultGenerator.genSuccessResult(roleService.findById(id));
    }

    @PostMapping
    public Result store(@RequestBody Role role) {
        roleService.save(role);
        for (Permission permission : role.getPermissions()) {
            permissionService.save(role.getPermissions());
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result update(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        roleService.save(role);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result destroy(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }
}
