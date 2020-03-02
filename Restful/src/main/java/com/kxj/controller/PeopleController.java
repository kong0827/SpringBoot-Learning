package com.kxj.controller;

import com.kxj.entity.People;
import com.kxj.group.Group;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kxj
 * @date 2020/2/21 22:16
 * @desc
 */

@RestController
public class PeopleController {

    /**
     * 单个对象校验
     * @param
     * people
     * @param errors
     */
    @PostMapping("/people")
    public void addPeople(@Validated({Group.class}) @RequestBody People people, BindingResult errors) {
        errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
    }

    /**
     * 多个对象校验
     * 个功能方法上处理多个模型对象时，需添加多个验证结果对象
     * @param p1
     * @param errors1
     * @param p2
     * @param errors2
     */
    @RequestMapping("/peoples")
    public void addPeople2(@Validated({Group.class}) People p1, BindingResult errors1,
                           @Validated({Group.class}) People p2, BindingResult errors2) {
        errors1.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        System.out.println("--------------------------");
        errors2.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
    }
}
