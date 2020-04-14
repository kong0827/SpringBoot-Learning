package com.kxj.mapper;

import com.kxj.entity.Employee;
import com.kxj.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author kxj
 * @date 2020/4/14 0:10
 * @desc
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTemplateTest {

    @Autowired
    RedisTemplate<Object, Employee> redisTemplate;

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 将Employee进行序列化存储进redis
     */
    @Test
    public void testRedisSerializer() {
        Employee employee = employeeMapper.getEmployee(1);

        redisTemplate.opsForValue().set("emp", employee);

    }
}
