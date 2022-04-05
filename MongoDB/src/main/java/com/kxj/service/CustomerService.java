package com.kxj.service;

import com.kxj.CustomerGroupDTO;
import com.kxj.dao.CustomerRepository;
import com.kxj.entity.Address;
import com.kxj.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.tools.tree.AndExpression;

import java.util.List;

/**
 * @author kxj
 * @date 2022/4/3 22:08
 * @desc
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void testTransaction(Customer customer) {
        customerRepository.save(customer);
        int i = 1 / 0;
    }

    /**
     * 根据年龄进行分组，求各年龄段总金额
     * 按照年龄降序，求总金额大于800的
     */
    public void group() {
        GroupOperation groupOperation = Aggregation.group(Customer.Fields.age).sum(Customer.Fields.money).as(CustomerGroupDTO.Fields.totalMoney);
        MatchOperation matchOperation = Aggregation.match(Criteria.where(CustomerGroupDTO.Fields.totalMoney).gt(800));
        SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, CustomerGroupDTO.Fields.totalMoney));
        ProjectionOperation projectionOperation = Aggregation.project().andExpression("_id").as(CustomerGroupDTO.Fields.age);
        Aggregation aggregation = Aggregation.newAggregation(groupOperation, matchOperation, sortOperation);
        AggregationResults<CustomerGroupDTO> aggregate = mongoTemplate.aggregate(aggregation, Customer.class, CustomerGroupDTO.class);
        List<CustomerGroupDTO> groupDTOList = aggregate.getMappedResults();
        for (CustomerGroupDTO customerGroupDTO : groupDTOList) {
            System.out.println(customerGroupDTO.toString());
        }
    }

    /**
     * 求年龄段人数分布的最小值和最大值
     *
     */
    public void groupAndAvg() {
        GroupOperation groupOperation = Aggregation.group(Customer.Fields.age).count().as(CustomerGroupDTO.Fields.ageCount);
        SortOperation sortByCount = Aggregation.sort(Sort.Direction.ASC, CustomerGroupDTO.Fields.ageCount);
//        ProjectionOperation projectionOperation = Aggregation.project()
        GroupOperation groupOperation1 = Aggregation.group().first("_id").as(CustomerGroupDTO.Fields.minAge).first(CustomerGroupDTO.Fields.ageCount).as(CustomerGroupDTO.Fields.minAgeCount)
                .last("_id").as(CustomerGroupDTO.Fields.maxAge).last(CustomerGroupDTO.Fields.ageCount).as(CustomerGroupDTO.Fields.maxAgeCount);

        Aggregation aggregation = Aggregation.newAggregation(groupOperation, sortByCount, groupOperation1);
        AggregationResults<CustomerGroupDTO> aggregate = mongoTemplate.aggregate(aggregation, Customer.class, CustomerGroupDTO.class);
        for (CustomerGroupDTO mappedResult : aggregate.getMappedResults()) {
            System.out.println(mappedResult.toString());
        }
    }

}
