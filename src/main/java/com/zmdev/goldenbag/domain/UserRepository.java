package com.zmdev.goldenbag.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User as u where u.id != :ignoreId and u.name like %:keyword% or u.phone like %:keyword%")
    List<User> search(@Param("keyword") String keyword, @Param("ignoreId") Long ignoreId, Pageable pageable);
}
