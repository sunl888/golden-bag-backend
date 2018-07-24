package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.Quarter;
import com.zmdev.goldenbag.domain.QuarterRepository;
import com.zmdev.goldenbag.service.QuarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuarterServiceImpl implements QuarterService {

    private final QuarterRepository quarterRepository;

    public QuarterServiceImpl(@Autowired QuarterRepository quarterRepository) {
        this.quarterRepository = quarterRepository;
    }

    @Override
    public Page<Quarter> findAllByPage(Pageable pageable) {
        return quarterRepository.findAll(pageable);
    }
}
