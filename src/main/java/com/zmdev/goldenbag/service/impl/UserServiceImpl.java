package com.zmdev.goldenbag.service.impl;

import com.zmdev.fatesdk.pb.CertificateType;
import com.zmdev.goldenbag.domain.Permission;
import com.zmdev.goldenbag.domain.Role;
import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.domain.UserRepository;
import com.zmdev.goldenbag.exception.ServiceException;
import com.zmdev.goldenbag.service.DepartmentService;
import com.zmdev.goldenbag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {

    private DepartmentService departmentService;

    private com.zmdev.fatesdk.UserService fateUserService;

    public UserServiceImpl(@Autowired DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Autowired
    public void setFateUserService(com.zmdev.fatesdk.UserService fateUserService) {
        this.fateUserService = fateUserService;
    }

    public List<User> search(String keyword, Long ignoreId) {
        if ("".equals(keyword)) {
            return new ArrayList<>();
        }
        if (ignoreId == null) {
            return repository.search(keyword, PageRequest.of(0, 10));
        } else {
            return repository.search(keyword, ignoreId, PageRequest.of(0, 10));
        }
    }

    @Override
    public List<User> findByDirectManager(User user) {
        return repository.findByDirectManager(user);
    }

    @Override
    public List<User> findByIndirectManager(User user) {
        return repository.findByIndirectManager(user);
    }


    public User update(Long id, User user) {

        if (user.getIndirectManager() != null && id.equals(user.getIndirectManager().getId())) {
            throw new ServiceException("间接经理不能是自己！");
        }
        if (user.getDirectManager() != null && id.equals(user.getDirectManager().getId())) {
            throw new ServiceException("直接经理不能是自己！");
        }

        user.setId(id);
        if (user.getDepartmentIds().size() > 0) {
            Long departmentId = user.getDepartmentIds().get(user.getDepartmentIds().size() - 1);
            user.setDepartment(departmentService.findById(departmentId).orElse(null));
        }
        return repository.save(user);
    }

    @Override
    public boolean hasPermission(User user, Permission permission) {
        List<Permission> p = new ArrayList<>();
        p.add(permission);
        return hasPermission(user, p);
    }

    @Override
    public boolean hasPermission(User user, List<Permission> permissions) {
        if (user == null) {
            return false;
        }
        List<Permission> userAllPermission = getUserAllPermission(user);
        return userAllPermission.containsAll(permissions);
    }

    @Override
    public boolean hasRole(User user, Role role) {
        return !((user == null) || (user.getRoles() == null)) && user.getRoles().contains(role);
    }

    @Override
    public boolean hasRole(User user, List<Role> roles) {
        return !((user == null) || (user.getRoles() == null)) && user.getRoles().containsAll(roles);
    }

    @Override
    public List<Permission> getUserAllPermission(User user) {
        Set<Permission> userAllPermission = new HashSet<>();
        List<Permission> userAllPermissionList = new ArrayList<>();

        if (user == null || user.getRoles() == null) {
            return userAllPermissionList;
        }

        for (Role role : user.getRoles()) {
            userAllPermission.addAll(role.getPermissions());
        }
        userAllPermissionList.addAll(userAllPermission);
        Collections.sort(userAllPermissionList);
        return userAllPermissionList;
    }

    public User save(User user) {
        if (user.getId() == null) {
            Long userId = fateUserService.register(user.getPhone(), CertificateType.PhoneNum, user.getPassword());
            user.setId(userId);
        }
        user.setPassword("");
        return super.save(user);
    }

}

