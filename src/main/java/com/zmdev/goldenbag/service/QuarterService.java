package com.zmdev.goldenbag.service;

import com.zmdev.goldenbag.domain.Quarter;

import java.util.ArrayList;
import java.util.Map;

public interface QuarterService extends BaseService<Quarter, Long> {
    Quarter findCurrentQuarter();

    Map<Integer, ArrayList<Quarter>> findAllByYear();
}
