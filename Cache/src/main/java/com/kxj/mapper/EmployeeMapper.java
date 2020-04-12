package com.kxj.mapper;

import com.kxj.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author kxj
 * @date 2020/4/12 21:03
 * @desc
 */
@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where id = #{id}")
    public Employee getEmployee(Integer id);

    @Update("update employee set lastName = {lastName}, email=#{email}, gender={gender}, where id={id}")
    public void updateEmployee(Employee employee);
}
