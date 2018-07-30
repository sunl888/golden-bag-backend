package com.zmdev.goldenbag.service.impl;


import com.zmdev.goldenbag.domain.Role;
import com.zmdev.goldenbag.domain.RoleRepository;
import com.zmdev.goldenbag.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long, RoleRepository> implements RoleService {
    public Role findByName(String name) {
        return repository.findByName(name);
    }
}
