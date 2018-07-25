package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.User;

import java.util.List;


public interface UserService extends BaseService<User, Long> {
    List<User> search(String keyword);
    void update(Long id, User user);
}
