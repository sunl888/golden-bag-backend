package com.zmdev.goldenbag.domain;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department, Long> {
    public Department findByName(String name);
}
