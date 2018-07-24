package com.zmdev.goldenbag.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuarterRepository extends JpaRepository<Quarter, Long> {
    Page<Quarter> findAll(Pageable pageable);
}
