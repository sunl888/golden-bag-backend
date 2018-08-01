package com.zmdev.goldenbag.web.basic;

import com.zmdev.goldenbag.domain.Permission;
import com.zmdev.goldenbag.domain.Role;
import com.zmdev.goldenbag.service.RoleService;
import com.zmdev.goldenbag.web.insterceptor.PermissionInterceptor;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "roles")
public class RoleController {

    private RoleService roleService;

    public RoleController() {
        PermissionInterceptor.addSpecialAbilitie(getClass(), "permission", "view");
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public Result index(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResultGenerator.genSuccessResult(roleService.findAllByPage(
                PageRequest.of(page, size,
                        new Sort(Sort.Direction.DESC, "createdAt")
                )
        ));
    }

    @GetMapping(value = "/{id}")
    public Result show(@PathVariable Long id) {
        Role role = roleService.findById(id).orElse(null);
        List<Long> l = new ArrayList<>();

        if (role != null && role.getPermissions() != null) {
            for (Permission p : role.getPermissions()) {
                l.add(p.getId());
            }
        }
        return ResultGenerator.genSuccessResult(role).addMeta("permissions", l);
    }

    @GetMapping(value = "/{id}/permissions")
    public Result permission(@PathVariable Long id) {
        Role role = roleService.findById(id).orElse(null);
        List<Permission> permissions = new ArrayList<>();
        if (role != null) {
            permissions = role.getPermissions();
        }
        return ResultGenerator.genSuccessResult(permissions);
    }

    @PostMapping
    public Result store(@RequestBody Role role) {
        Role storedRole = roleService.save(role);
        return ResultGenerator.genSuccessResult(storedRole);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Result update(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        return ResultGenerator.genSuccessResult(roleService.save(role));
    }

    @DeleteMapping("/{id}")
    public Result destroy(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }
}
