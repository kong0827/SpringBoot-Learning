package com.kxj.mybatis;

import com.kxj.mybatis.bean.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

//@CacheNamespace 开启二级缓存
public interface UserMapper {

    @Select({"select * from users where id=#{1}"})
    User selectByid(Integer id);


    @Select({"select * from users where id=#{1}"})
    User selectByid3(Integer id);

    @Select({" select * from users where name='${name}'"})
    @Options(statementType = StatementType.PREPARED)
    List<User> selectByName(User user);

    List<User> selectByUser(User user);

    @Insert("INSERT INTO `users`( `name`, `age`, `sex`, `email`, `phone_number`) VALUES ( #{name}, #{age}, #{sex}, #{email}, #{phoneNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addUser(User user);

    int editUser(User user);

    @Update("update  users set name=#{arg1} where id=#{arg0}")
    int setName(Integer id, String name);

    @Delete("delete from users where id=#{id}")
    int deleteUser(Integer id);
}
