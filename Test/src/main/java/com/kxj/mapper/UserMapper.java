package com.kxj.mapper;

import com.kxj.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiangjin.kong
 * @date 2021/6/10 14:07
 */
@Mapper
public interface UserMapper {

    void insert(User user);
}
