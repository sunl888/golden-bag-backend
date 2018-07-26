package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.Quarter;

public interface QuarterService extends BaseService<Quarter, Long> {
    Quarter findCurrentQuarter();
}
