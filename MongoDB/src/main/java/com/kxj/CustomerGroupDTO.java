package com.kxj;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

/**
 * @author kxj
 * @date 2022/4/5 22:49
 * @desc
 */
@Data
@ToString
@FieldNameConstants
public class CustomerGroupDTO {

    // @Id注释将 group中_id 字段从输出映射到模型中的状态：
    // _id是分组字段
//    @Id
    private Integer age;

    private BigDecimal totalMoney;

    private Integer ageCount;

    private Integer maxAge;

    private BigDecimal maxAgeCount;

    private Integer minAge;

    private BigDecimal minAgeCount;
}
