package com.zmdev.goldenbag.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByName(String name);

    List<Department> findByParentId(Long parent_id);
}
