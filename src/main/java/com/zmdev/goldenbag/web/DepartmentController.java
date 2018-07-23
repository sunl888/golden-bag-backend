package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.Department;
import com.zmdev.goldenbag.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/departments", produces = "application/json;charset=UTF-8")
public class DepartmentController extends BaseController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/")
    @ResponseBody
    public List<Department> index() {
        List<Department> departments = departmentService.findAll();
        return departments;
    }
}
