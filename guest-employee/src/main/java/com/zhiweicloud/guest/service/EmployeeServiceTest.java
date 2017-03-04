package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.model.Dropdownlist;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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

    @Test
    public void getEmployeeDropdownListByRoleId() throws  Exception {
        List<Dropdownlist> list =employeeService.getEmployeeDropdownListByRoleId("LJG",18L);
        List<Dropdownlist> list2 =employeeService.getEmployeeDropdownListByRoleId("LJG",null);
        System.out.println(111);
    }

}