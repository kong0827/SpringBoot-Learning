package com.kxj.swagger.entity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @ClassName User
 * @description TODO
 * @Author kongxiangjin
 * @Date 2019/12/19 13:46
 * @Version 1.0
 **/


@ApiModel("用户实体类")
public class User {

    @ApiModelProperty("用户名")
    public String username;
    @ApiModelProperty("密码")
    public String password;

}
