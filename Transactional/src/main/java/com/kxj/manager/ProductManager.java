package com.kxj.manager;

import com.kxj.entity.Product;
import com.kxj.mapper.ProductMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author xiangjin.kong
 * @date 2020/11/2 15:04
 */
@Component
public class ProductManager {
    @Resource
    private ProductMapper productMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(Product product) {
        productMapper.insert(product);
    }
}
