package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.Menu;
import com.zmdev.goldenbag.domain.Permission;
import com.zmdev.goldenbag.domain.PermissionRepository;
import com.zmdev.goldenbag.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long, PermissionRepository> implements PermissionService {
    @Override
    public Map<String, Map<String, List<Permission>>> formatList(List<Permission> permissions) {
        Map<String, Map<String, List<Permission>>> formatData = new HashMap<>();
        for (Permission permission : permissions) {
            String topModuleName = Permission.getModules().get(permission.getTopModuleName());
            String moduleName = Permission.getModules().get(permission.getModuleName());

            if (!formatData.containsKey(topModuleName)) {
                Map<String, List<Permission>> m = new HashMap<>();
                // List<Permission> l = new ArrayList<>();
                //l.add(permission);
                //m.put(moduleName, l);
                formatData.put(topModuleName, m);
            }

            Map<String, List<Permission>> m = formatData.get(topModuleName);
            if (!m.containsKey(moduleName)) {
                List<Permission> l = new ArrayList<>();
                l.add(permission);
                m.put(moduleName, l);
            } else {
                m.get(moduleName).add(permission);
            }

        }
        return formatData;
    }

    @Override
    public List<Menu> convertToMenus(List<Permission> permissions) {
        List<Menu> menus = new ArrayList<>();
        for (Permission permission : permissions) {
            if (!permission.getMenuable())
                continue;
            String topModuleName = permission.getTopModuleName();
            // 直接new Menu比较的原因是 Menu类我已经复写了 equals() 和 hashCode() 方法
            Menu topMenu = new Menu();
            topMenu.setName(topModuleName);
            if (!menus.contains(topMenu)) {
                topMenu.setDisplayName(Permission.getModules().get(topModuleName));
                menus.add(topMenu);
            } else {
                topMenu = menus.get(menus.indexOf(topMenu));
            }

            String moduleName = permission.getModuleName();
            Menu subMenu = new Menu();
            subMenu.setName(moduleName);
            if (!topMenu.getChildren().contains(subMenu)) {
                subMenu.setDisplayName(Permission.getModules().get(moduleName));
                topMenu.getChildren().add(subMenu);
            }
        }
        return menus;
    }

    public Permission findByName(String name) {
        return repository.findByName(name);
    }
}
