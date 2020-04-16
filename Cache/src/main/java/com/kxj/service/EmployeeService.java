package com.kxj.service;

import com.kxj.entity.Employee;
import com.kxj.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author kxj
 * @date 2020/4/12 21:45
 * @desc
 */
@Service
/**
 * 抽取缓存的公共配置
 * cacheNames
 */
@CacheConfig
public class EmployeeService {

    @Autowired

     EmployeeMapper employeeMapper;

    /**
     * @param id
     * @return
     * @Cacheable 讲方法的运行结果进行缓存
     * cacheNames/value: 指定缓存组件的名字
     * key: 缓存数据使用的key，默认是使用方法的参数的值
     * 编写SpEL: #id:参数id的值。 等同于#a0，#p0, #root.getarg[0]
     * keyGenerator: key的生成器。keyGenerator和key只能存在一个
     * cacheManager：指定缓存管理器
     * condition：指定符合情况的条件下才缓存
     * unless： unless指定的条件为true，就不缓存。可以获取到结果判断
     * sync：是否使用异步
     */
    @Cacheable(cacheNames = "emp")
    public Employee getEmployee(Integer id) {
        System.out.println("查询" + id + "员工");
        Employee employee = employeeMapper.getEmployee(id);
        return employee;
    }

    /**
     * CachePut
     * 既调用方法，又更新缓存
     * key="#result.id" 或者 key="#employee.id" 保持缓存中值更新了，键是一样的。否则无法触发缓存更新
     *
     * @param employee
     * @return
     */
    @CachePut(value = "emp", key = "#employee.id")
    public Employee updateEmployee(Employee employee) {
        System.out.println("更新" + employee.getId() + "员工");
        employeeMapper.updateEmployee(employee);
        return employee;
    }

    /**
     * CacheEvict
     * 缓存清除
     * key: 指定清除的缓存
     * allEntries: 默认false 为true删除所有缓存
     * beforeInvocation: 缓存的清除是否在方法之前执行。默认缓存在方法执行之后执行，如果出现异常缓存就不会清除
     * @param id
     */
    @CacheEvict(value = "emp")
    public void deleteEmployee(Integer id) {
        System.out.println("删除" + id + "号员工");
//        employeeMapper.deleteEmployee(id);
    }

    @Caching(
            cacheable = {
                    @Cacheable(value="emp", key="#lastName")
            },
            put = {
                  @CachePut(value = "emp", key = "#result.id"),
                  @CachePut(value = "emp", key = "#result.email")
            }
    )
    public Employee getEmployee(String lastName) {
        System.out.println("查询" + lastName + "员工");
        Employee employee = employeeMapper.getEmployeeByLastName(lastName);
        return employee;
    }
}
