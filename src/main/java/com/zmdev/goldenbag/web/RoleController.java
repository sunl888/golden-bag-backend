package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.Role;
import com.zmdev.goldenbag.service.RoleService;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "roles", name = "roles")
public class RoleController {

    private RoleService roleService;

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

    @GetMapping(value = "/{id}", name = "show")
    public Result show(@PathVariable Long id) {
        return ResultGenerator.genSuccessResult(roleService.findById(id));
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
