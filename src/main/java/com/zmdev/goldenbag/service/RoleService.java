package com.zmdev.goldenbag.service;


import com.zmdev.goldenbag.domain.Role;

public interface RoleService extends BaseService<Role, Long> {
    Role findByName(String name);
}
