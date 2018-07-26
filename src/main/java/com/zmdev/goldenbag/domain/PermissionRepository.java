package com.zmdev.goldenbag.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByShortLogMessage(String shortLogMessage);
}
