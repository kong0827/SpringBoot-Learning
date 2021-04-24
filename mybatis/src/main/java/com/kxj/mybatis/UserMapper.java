package com.kxj.mybatis;

import com.kxj.mybatis.bean.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.FifoCache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@CacheNamespace //开启二级缓存
/*@CacheNamespace(implementation = DiskCache.class, properties = {@Property(name = "cachePath",
        value ="E:\\githubResp\\SpringBoot-Demo\\mybatis\\src\\main\\resources" )}) //更换实现方式*/
//@CacheNamespace(eviction = FifoCache.class, size = 10) // 设置缓存淘汰策略
//@CacheNamespace(readWrite=false)
//@CacheNamespace(flushInterval = 10000, blocking=true)
public interface UserMapper {

    @Select({"select * from users where id=#{id}"})
    User selectById(Integer id);

    @Select({"select * from users where id=#{id}"})
    User selectById1(@Param("id") Integer id);


    @Select({"select * from users where id=#{1} and name = #{name}"})
    User selectByIdAndName(Integer id, String name);


    @Select({"select * from users where id=#{1}"})
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    User selectById3(Integer id);

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
