package com.kxj.dao;

import com.kxj.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author xiangjin.kong
 * @date 2020/9/7 14:35
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerRepositoryTest {


    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void insertTest() {
        Customer customer = new Customer(3, "wanger");
        customerRepository.insert(customer);
    }

    @Test
    public void findAllTest() {
        List<Customer> customers = customerRepository.findAll();
        customers.stream().forEach(customer -> System.out.println());
    }

    @Test
    public void asyncTest() throws ExecutionException, InterruptedException {
        Future<Customer> future = customerRepository.findByName("zhangsan");
        Customer customer = future.get();
        System.out.println(customer);

        while (true) {
            if (future.isDone()) {
                Customer customer1 = future.get(); break;
            }
        }
    }

}
