package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();

    List<Department> findTopDepartment();

    Department save(Department department);
}
