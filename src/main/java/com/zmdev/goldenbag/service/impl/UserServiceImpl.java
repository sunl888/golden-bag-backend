package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.domain.UserRepository;
import com.zmdev.goldenbag.exception.ServiceException;
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

}

