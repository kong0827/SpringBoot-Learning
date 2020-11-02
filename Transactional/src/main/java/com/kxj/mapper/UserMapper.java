package com.kxj.mapper;

import com.kxj.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiangjin.kong
 * @date 2020/11/2 14:13
 */
@Mapper
public interface UserMapper {

    void insert(User user);
}
