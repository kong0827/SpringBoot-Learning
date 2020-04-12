package com.kxj.service;

import com.kxj.entity.Employee;
import com.kxj.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author kxj
 * @date 2020/4/12 21:45
 * @desc
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * @Cacheable
     * 讲方法的运行结果进行缓存
     * cacheNames/value: 指定缓存组件的名字
     * key: 缓存数据使用的key，默认是使用方法的参数的值
     *      编写SpEL: #id:参数id的值。 等同于#a0，#p0, #root.getarg[0]
     * keyGenerator: key的生成器。keyGenerator和key只能存在一个
     * cacheManager：指定缓存管理器
     * condition：指定符合情况的条件下才缓存
     * unless： unless指定的条件为true，就不缓存。可以获取到结果判断
     * sync：是否使用异步
     *
     *
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "emp")
    public Employee getEmployee(Integer id) {
        System.out.println("查询" + id + "员工");
        Employee employee = employeeMapper.getEmployee(id);
        return employee;
    }
}
