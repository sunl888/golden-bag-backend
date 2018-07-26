package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.AssessmentInput;
import com.zmdev.goldenbag.domain.AssessmentInputRepository;
import com.zmdev.goldenbag.service.AssessmentInputService;
import org.springframework.stereotype.Service;

@Service
public class AssessmentInputServiceImpl extends BaseServiceImpl<AssessmentInput, Long, AssessmentInputRepository> implements AssessmentInputService {
}
