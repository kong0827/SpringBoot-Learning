package com.kxj.dao;

import com.kxj.entity.Customer;
import com.kxj.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    CustomerService customerService;

    @Test
    public void insertTest() {
        Customer customer = Customer.builder().name("kxj").age(18).build();
        customerRepository.insert(customer);
    }

    @Test
    public void testTransaction() {
        Customer customer = Customer.builder().name("kxj").age(18).build();
        customerService.testTransaction(customer);
    }

    @Test
    public void findAllTest() {
        List<Customer> customers = customerRepository.findAll();
        customers.forEach(System.out::println);
    }

    @Test
    public void asyncTest() throws ExecutionException, InterruptedException {
        Future<Customer> future = customerRepository.findByName("zhangsan");
        Customer customer = future.get();
        System.out.println(customer);

        while (true) {
            if (future.isDone()) {
                Customer customer1 = future.get();
                break;
            }
        }
    }


    @Test
    public void testGroup() {
        customerService.group();
    }

    @Test
    public void testGroupAndAvg() {
        customerService.groupAndAvg();
    }

}
