package com.zm.goldenbag.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    public Department findByName(String name);
}
