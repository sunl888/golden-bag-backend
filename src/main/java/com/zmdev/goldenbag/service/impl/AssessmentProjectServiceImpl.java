package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.AssessmentProject;
import com.zmdev.goldenbag.domain.AssessmentProjectRepository;
import com.zmdev.goldenbag.service.AssessmentProjectService;
import org.springframework.stereotype.Service;

@Service
public class AssessmentProjectServiceImpl extends BaseServiceImpl<AssessmentProject, Long, AssessmentProjectRepository> implements AssessmentProjectService {
}
