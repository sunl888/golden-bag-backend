package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.Permission;
import com.zmdev.goldenbag.domain.User;

import java.util.List;


public interface UserService extends BaseService<User, Long> {

    boolean hasPermission(User user, Permission permission);

    boolean hasPermission(User user, List<Permission> permissions);

    List<User> search(String keyword, Long ignoreId);

    User update(Long id, User user);

    List<Permission> getUserAllPermission(User user);
}
