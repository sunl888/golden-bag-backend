package com.zmdev.goldenbag.service.impl;

import com.zmdev.goldenbag.domain.DepartmentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DepartmentServiceImplTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void deleteById() {

        /*Department p1 = new Department("开发部-1", null);
        departmentRepository.save(p1);*/
        /*Department p2 = new Department("項目組-1", p1);
        departmentRepository.save(p2);
        Department p3 = new Department("項目組-1-1", p2);
        departmentRepository.save(p3);
        Department p4 = new Department("項目組-1-1-1", p3);
        departmentRepository.save(p3);

        Department p5 = new Department("开发部-2", null);
        departmentRepository.save(p5);
        Department p6 = new Department("項目組-2", p5);
        departmentRepository.save(p6);
        Department p7 = new Department("項目組-2", p6);
        departmentRepository.save(p7);*/
        //Assert.assertEquals(1L, (long) p1.getId());
        //departmentRepository.deleteById(p1.getId());
        //departmentRepository.findByName("开发部-1");
    }
}