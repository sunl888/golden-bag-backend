package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.Department;
import com.zmdev.goldenbag.domain.DepartmentRepository;
import com.zmdev.goldenbag.exception.ConstraintException;
import com.zmdev.goldenbag.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department, Long, DepartmentRepository> implements DepartmentService {

    public void deleteById(Long id) {
        int childDepartmentSize = repository.findByParentId(id).size();
        if (childDepartmentSize != 0) {
            throw new ConstraintException("删除失败，该部门已有子部门");
        }
        repository.deleteById(id);
    }

    public List<Department> findAllByParent(Department department) {
        return repository.findByParent(department);
    }
}

