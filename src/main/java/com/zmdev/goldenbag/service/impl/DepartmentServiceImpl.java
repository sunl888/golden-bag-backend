package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.Department;
import com.zmdev.goldenbag.domain.DepartmentRepository;
import com.zmdev.goldenbag.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public List<Department> findTopDepartment() {
        return departmentRepository.findByParent(null);
    }

    public Department save(Department department) {
        return departmentRepository.save(department);
    }


}
