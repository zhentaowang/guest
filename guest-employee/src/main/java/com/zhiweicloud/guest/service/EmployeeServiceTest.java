package com.zhiweicloud.guest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by luojing@wyunbank.com on 09/02/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
@ActiveProfiles("production")
public class EmployeeServiceTest {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    Environment environment;

    @Autowired
    ApplicationContext context;

    @Test
    public void getById() throws Exception {
        employeeService.getById(1L, "LJG");
    }

}