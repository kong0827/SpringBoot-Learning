package com.kxj.mapper;

import com.kxj.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author kxj
 * @date 2020/4/12 21:12
 * @desc
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeMapperTest {

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void test() {
        Employee employee = employeeMapper.getEmployee(1);
        System.out.println(employee);
    }
}
