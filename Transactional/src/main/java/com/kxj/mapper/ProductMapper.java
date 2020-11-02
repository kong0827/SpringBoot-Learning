package com.kxj.mapper;

import com.kxj.entity.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xiangjin.kong
 * @date 2020/11/2 15:05
 */
@Mapper
public interface ProductMapper {
    void insert(Product product);
}
