package com.kxj.mapper;

import com.kxj.entity.Product;
import com.kxj.manager.ProductManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author xiangjin.kong
 * @date 2020/11/2 15:24
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductTest {

    @Autowired
    ProductManager productManager;

    @Test
    public void insertTest() {
        Product product = new Product();
        product.setProductName("商品");

        productManager.insert(product);
    }
}
