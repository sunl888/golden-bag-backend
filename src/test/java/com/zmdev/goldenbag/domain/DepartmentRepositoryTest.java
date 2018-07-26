package com.zmdev.goldenbag.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testSave() {
        Department pDepartment = new Department("开发部门", null);
        departmentRepository.save(pDepartment);
        departmentRepository.save(new Department("开发组1", pDepartment));
        departmentRepository.save(new Department("开发组2", pDepartment));
        departmentRepository.save(new Department("开发组3", pDepartment));

        Department d = departmentRepository.findByName("开发部门");

        Assert.assertEquals(d.getName(), "开发部门");
    }

    @Test
    public void testFindAll() {
        List<Department> departments = departmentRepository.findAll();
        for (Department item : departments) {
            System.out.println(item.getName());
        }
    }

}
