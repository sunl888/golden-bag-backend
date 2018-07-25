package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.domain.UserRepository;
import com.zmdev.goldenbag.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {
    public List<User> search(String keyword) {
        if ("".equals(keyword)) {
            return new ArrayList<>();
        }

        return repository.search(keyword, PageRequest.of(0, 10));
    }
}

