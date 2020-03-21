package com.kxj.swagger.controller;

import com.kxj.swagger.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * @author kxj
 * @date 2020/3/21 22:38
 * @desc
 */
@RequestMapping("user")
@RestController
@Api(tags = "用户信息")
public class UserController {

    @GetMapping
    public User getUser() {
        return new User();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public Object deleteUser(@ApiParam("用户ID") @PathVariable("id")Integer id) {
        return true;
    }
}
