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

    private Department HandleParentIds(Department department) {

        department.setChildren(null);
        if (department.getParentIds().size() > 0) {
            Long parentId = department.getParentIds().get(department.getParentIds().size() - 1);
            department.setParent(repository.findById(parentId).orElse(null));
        }
        return department;
    }

    public Department save(Department department) {
        department.setId(null);
        department = this.HandleParentIds(department);
        return repository.save(department);
    }

    public Department update(Long id, Department department) {
        department.setId(id);

        department = this.HandleParentIds(department);
        return repository.save(department);
    }


}

