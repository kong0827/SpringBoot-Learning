package com.kxj.controller;

import com.kxj.entity.Employee;
import com.kxj.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kxj
 * @date 2020/4/12 21:47
 * @desc
 */
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("employee/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmployee(id);
        return employee;
    }

    @GetMapping("employee")
    public Employee updateEmployee(Employee employee) {
        Employee emp = employeeService.updateEmployee(employee);
        return emp;
    }

    @GetMapping("employee/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeService.deleteEmployee(id);
        return "success";
    }

    @GetMapping("employee/lastName/{lastName}")
    public Employee getEmployee(@PathVariable("lastName") String lastName) {
        Employee employee = employeeService.getEmployee(lastName);
        return employee;
    }
}
