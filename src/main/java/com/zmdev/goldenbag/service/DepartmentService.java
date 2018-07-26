package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.Department;

import java.util.List;

public interface DepartmentService extends BaseService<Department, Long> {

    List<Department> findAllByParent(Department department);

    Department save(Department department);

    Department update(Long id, Department department);
}
