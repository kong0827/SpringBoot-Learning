package com.kxj.dao;

import com.kxj.entity.Customer;
import jdk.nashorn.internal.objects.annotations.Where;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author xiangjin.kong
 * @date 2020/9/7 16:36
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class QueryInstanceTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void findTest() {
        BasicQuery basicQuery = new BasicQuery("{id: {$lt:50}}");
        List<Customer> customers = mongoTemplate.find(basicQuery, Customer.class);
        customers.stream().forEach(customer -> System.out.println(customer));

        BasicQuery basicQuery1 = new BasicQuery("{id:1}");
        Customer customer = mongoTemplate.findOne(basicQuery, Customer.class);
        System.out.println(customer);
    }

    /**
     * 所有查找方法都将Query对象作为参数。该对象定义用于执行查询的条件和选项。使用Criteria具有静态工厂方法的对象指定标准，
     * 该方法名为where实例化新Criteria对象。我们建议对org.springframework.data.mongodb.core.query.Criteria.where和使用静态导入，
     * Query.query以提高查询的可读性。
     */
    @Test
    public void testByCriteriaTest() {
        List<Customer> customers = mongoTemplate.find(Query.query(Criteria.where("id").lt(50).and("name").is("lisi")), Customer.class);
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void test() {
        List<Object> customers = mongoTemplate.query(Customer.class).distinct("name").all();
        for (Object customer : customers) {
            System.out.println(customer);
        }

        System.out.println("-------------------");

        List<String> list = mongoTemplate.query(Customer.class)
                .distinct("name")
                .as(String.class)
                .all();
        for (String s : list) {
            System.out.println(s);
        }
    }
}
