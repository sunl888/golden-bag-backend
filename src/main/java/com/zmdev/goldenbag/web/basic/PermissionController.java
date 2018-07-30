package com.zmdev.goldenbag.web.basic;

import com.zmdev.goldenbag.domain.Permission;
import com.zmdev.goldenbag.service.PermissionService;
import com.zmdev.goldenbag.service.UserService;
import com.zmdev.goldenbag.web.Auth;
import com.zmdev.goldenbag.web.BaseController;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController extends BaseController {
    private PermissionService permissionService;
    private Auth auth;
    private UserService userService;

    @Autowired
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Autowired
    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Result index() {
        return ResultGenerator.genSuccessResult(permissionService.formatList(permissionService.findAll(new Sort(Sort.Direction.DESC, "createdAt"))));
    }

    @GetMapping("/menus")
    public Result allMenus() {
        // todo 根据用户权限获取相应的权限
        List<Permission> permissions = userService.getUserAllPermission(auth.getUser());
        return ResultGenerator.genSuccessResult(permissionService.convertToMenus(permissions));
    }
}
