package com.zmdev.goldenbag.service.impl;

import com.sun.xml.internal.ws.model.RuntimeModelerException;
import com.zmdev.goldenbag.domain.Department;
import com.zmdev.goldenbag.domain.DepartmentRepository;
import com.zmdev.goldenbag.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department, Long, DepartmentRepository> implements DepartmentService {


    public void deleteById(Long id) {
        int childDepartmentSize = repository.findByParentId(id).size();
        if (childDepartmentSize != 0) {
            throw new RuntimeModelerException("删除失败，该部门已有子部门");
        }
        repository.deleteById(id);
    }
}

