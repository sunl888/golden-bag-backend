package com.zmdev.goldenbag.domain;


import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(String name);
}
