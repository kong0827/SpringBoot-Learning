package com.kxj.controller;

import com.kxj.entity.Employee;
import com.kxj.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kxj
 * @date 2020/4/12 21:47
 * @desc
 */
@RestController("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Employee getEmployee(Integer id) {
        Employee employee = employeeService.getEmployee(id);
        return employee;
    }
}
