package com.kxj.dao;

import com.kxj.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xiangjin.kong
 * @date 2020/9/7 14:33
 */
@Repository
public interface CustomerRepository extends MongoRepository<Customer, Integer> {

}
