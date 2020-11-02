package com.kxj.manager;

import com.kxj.entity.Product;
import com.kxj.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author xiangjin.kong
 * @date 2020/11/2 15:08
 */
@Component
public class OrderManager {
    @Resource
    private UserManager userManager;
    @Resource
    private ProductManager productManager;

    @Transactional(rollbackFor = Exception.class)
    public void createOrder(String userName, String productName) {
        User user = new User(null, userName);
        //插入用户信息
        userManager.insert(user);

        Product product = new Product(null, productName);

        //插入商品信息
        productManager.insert(product);
        throw new RuntimeException("fail to create order");
    }
}
