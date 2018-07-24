package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.Department;
import com.zmdev.goldenbag.service.DepartmentService;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Result<List<Department>> index() {
        return ResultGenerator.genSuccessResult(departmentService.findTopDepartment());
    }

    /**
     * 添加部門
     *
     * @param department
     * @return Response
     */
    @PostMapping
    @ResponseBody
    public Result store(@RequestBody Department department) {
        departmentService.save(department);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 刪除部門
     *
     * @param id
     * @return Response
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Result destroy(@PathVariable Long id) {
        System.out.println(id);
        departmentService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

}
