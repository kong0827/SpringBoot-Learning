package com.kxj.entity;

import com.kxj.group.First;
import com.kxj.group.Second;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author kxj
 * @date 2020/2/21 22:12
 * @desc
 */
@Data
public class People implements Serializable {

    // 在First分组，判断不能为空
    @NotEmpty(groups = {First.class}, message = "id不能为空")
    private String id;


    // name的字段不能为空，且长度在3-8位
    @NotEmpty(groups = {First.class}, message = "name不能为空")
    @Size(min = 3, max = 8, groups = {Second.class}, message = "name在3-8位之间")
    private String name;

}
