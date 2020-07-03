package com.kxj.dao.primary;

import com.kxj.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiangjin.kong
 * @date 2020/7/2 16:14
 * @desc
 */
@Mapper
public interface MasterUserDao {
    //View all Tuser information
    @Select("select * from user")
    List<User> selectUsers();

}
