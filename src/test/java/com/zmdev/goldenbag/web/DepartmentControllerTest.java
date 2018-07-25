package com.zmdev.goldenbag.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentControllerTest {

    /*private MockMvc mvc;

    @Autowired
    private DepartmentController departmentController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }*/

    /*@Test
    public void index() throws Exception {
        MvcResult a = mvc.perform(MockMvcRequestBuilders.get("/departments")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(a.getResponse().getContentAsString());
    }*/

    /*@Test
    public void testDetail() throws Exception {
        MvcResult mvcResult = mvc
                .perform(
                        MockMvcRequestBuilders.get("/departments/index")
                )
                .andReturn();
        //输出经历的拦截器
        HandlerInterceptor[] interceptors = mvcResult.getInterceptors();
        System.out.println(interceptors[0].getClass().getName());

        int status = mvcResult.getResponse().getStatus(); // 6
        String responseString = mvcResult.getResponse().getContentAsString(); // 7
        System.out.println("返回内容：" + responseString);
        Assert.assertEquals("return status not equals 200", 200, status); // 8
    }*/

    @Test
    public void store() {
    }

    @Test
    public void destroy() {

    }
}