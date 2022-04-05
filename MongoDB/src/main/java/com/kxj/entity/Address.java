package com.kxj.entity;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author kxj
 * @date 2022/4/3 21:22
 * @desc
 */
@Data
@FieldNameConstants
public class Address {

    @Field(value = "province_name")
    private String provinceName;

    @Field(value = "city_name")
    private String cityName;

    @Field(value = "area_name")
    private String areaName;

}
