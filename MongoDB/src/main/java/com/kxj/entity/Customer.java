package com.kxj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiangjin.kong
 * @date 2020/9/7 14:32
 * @desc :
 * {
 *     "_id": {
 *         "$oid": "62499da226d3f30566f4a70f"
 *     },
 *     "name": "zhangsan",
 *     "age": 10,
 *     "address": {
 *         "province_name": "安徽省",
 *         "city_name": "合肥市",
 *         "area_name": "长丰县"
 *     },
 *     "hobby": ["coding", "read"],
 *     "friends": [{
 *         "name": "李四",
 *         "sex": "男"
 *     }, {
 *         "name": "Tina",
 *         "sex": "女"
 *     }]
 * }
 */
@Data
@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customer")
public class Customer {

    @Id
    private String id;

    @Indexed(name = "idx_name")
    private String name;

    private Integer age;

    private Address address;

    private List<String> hobby;

    private List<Friend> friends;

    private BigDecimal money;
}
