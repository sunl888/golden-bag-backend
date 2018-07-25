package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.domain.UserRepository;
import com.zmdev.goldenbag.service.DepartmentService;
import com.zmdev.goldenbag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {

    private DepartmentService departmentService;

    public UserServiceImpl(@Autowired DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public List<User> search(String keyword) {
        if ("".equals(keyword)) {
            return new ArrayList<>();
        }

        return repository.search(keyword, PageRequest.of(0, 10));
    }

    public void update(Long id, User user) {
        user.setId(id);
        if (user.getDepartmentIds().size() > 0) {
            Long departmentId = user.getDepartmentIds().get(user.getDepartmentIds().size() - 1);
            user.setDepartment(departmentService.findById(departmentId).orElse(null));
        }
        repository.save(user);
    }

}

