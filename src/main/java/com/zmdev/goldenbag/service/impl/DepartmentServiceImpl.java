package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.Department;
import com.zmdev.goldenbag.domain.DepartmentRepository;
import com.zmdev.goldenbag.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department, Long, DepartmentRepository> implements DepartmentService {
}

