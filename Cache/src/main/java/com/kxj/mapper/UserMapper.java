package com.kxj.mapper;

import com.kxj.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author kxj
 * @date 2020/4/15 21:17
 * @desc
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    public User getUser(Integer id);

}
