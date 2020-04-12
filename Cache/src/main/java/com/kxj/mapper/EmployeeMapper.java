package com.kxj.mapper;

import com.kxj.entity.Employee;
import org.apache.ibatis.annotations.Delete;
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

    @Update("update employee set lastName = #{lastName}, email=#{email}, gender= #{gender}, d_id = #{dId} where id= #{id}")
    public void updateEmployee(Employee employee);

    @Delete("delete from employee where  id= #{id}")
    public void deleteEmployee(Integer id);

    @Select("select * from employee where lastName = #{lastName}")
    public Employee getEmployeeByLastName(String lastName);
}
