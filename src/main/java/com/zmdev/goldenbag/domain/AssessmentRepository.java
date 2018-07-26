package com.zmdev.goldenbag.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    @Query("select count(a.id) from Assessment a where a.user=:user and a.quarter = :quarter")
    int isSubmitedWithCurrentQuarter(@Param("user") User user, @Param("quarter") Quarter quarter);

}
