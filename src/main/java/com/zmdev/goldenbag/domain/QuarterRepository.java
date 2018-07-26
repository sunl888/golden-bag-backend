package com.zmdev.goldenbag.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuarterRepository extends JpaRepository<Quarter, Long> {

    @Query(value = "select q from Quarter q where q.currentQuarter = true ")
    Quarter findCurrentQuarter();
}
