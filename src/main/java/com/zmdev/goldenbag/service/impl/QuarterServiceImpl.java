package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.Quarter;
import com.zmdev.goldenbag.domain.QuarterRepository;
import com.zmdev.goldenbag.service.QuarterService;
import org.springframework.stereotype.Service;

@Service
public class QuarterServiceImpl extends BaseServiceImpl<Quarter, Long, QuarterRepository> implements QuarterService {
}
