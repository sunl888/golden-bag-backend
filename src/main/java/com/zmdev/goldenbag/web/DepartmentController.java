package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.Department;
import com.zmdev.goldenbag.domain.User;
import com.zmdev.goldenbag.domain.result.Response;
import com.zmdev.goldenbag.domain.result.ResponseData;
import com.zmdev.goldenbag.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/departments", produces = "application/json;charset=UTF-8")
public class DepartmentController extends BaseController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * 顯示部門以及項目組列表
     *
     * @return Response
     */
    @GetMapping
    @ResponseBody
    public Response index() {
        return new ResponseData(departmentService.findTopDepartment());
    }

    @PostMapping
    @ResponseBody
    public Response store(@RequestBody Department department) {
        departmentService.save(department);
        return new Response();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Response destroy(@PathVariable Long id) {
        System.out.println(id);
        departmentService.deleteById(id);
        return new Response();
    }

}
