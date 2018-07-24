package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.Quarter;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface QuarterService {
    Page<Quarter> findAllByPage(Pageable pageable);
}
