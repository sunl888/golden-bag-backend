package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.Menu;
import com.zmdev.goldenbag.domain.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends BaseService<Permission, Long> {
    Map<String, Map<String, List<Permission>>> formatList(List<Permission> permissions);

    List<Menu> convertToMenus(List<Permission> permissions);

    Permission findByName(String name);
}
