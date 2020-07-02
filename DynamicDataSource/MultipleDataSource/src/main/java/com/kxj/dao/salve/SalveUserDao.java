package com.kxj.dao.salve;

import com.kxj.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiangjin.kong
 * @date 2020/7/2 16:55
 * @desc
 */
@Repository
public interface SalveUserDao {
    //View all Tuser information
    @Select("select * from user")
    List<User> selectUsers();

}
