package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.Permission;
import com.zmdev.goldenbag.service.PermissionService;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController extends BaseController {
    private PermissionService permissionService;

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public Result index() {
        return ResultGenerator.genSuccessResult(permissionService.formatList(permissionService.findAll()));
    }

    @GetMapping("/menus")
    public Result allMenus() {
        // todo 根据用户权限获取相应的权限
        List<Permission> permissions = permissionService.findAll();
        return ResultGenerator.genSuccessResult(permissionService.convertToMenus(permissions));
    }
}
