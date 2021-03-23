package com.kxj.controller;

import com.kxj.entity.Teacher;
import com.kxj.group.First;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author kxj
 * @date 2020/2/20 22:49
 * @desc
 */
@RestController
@Validated
public class TeacherController {

    /**
     * 较少参数校验
     * @param username
     * @param age
     */
    @GetMapping("/teacher")
    public void getTeacher(@NotBlank(message = "用户名不能为空") String username,
                           @Min(value = 18, message = "年龄不能小于18岁") Integer age) {
        System.out.println("username:" + username + "    age:" + age);
    }

    /**
     * 实体类参数校验
     * @param teacher
     * @param errors
     */
    @PostMapping("/teacher")
    public void getTeacher(@Validated @RequestBody Teacher teacher,
                           BindingResult errors) {
        errors.getAllErrors().stream().forEach(error -> {
            System.out.println(error.getDefaultMessage());
        });
    }

    /**
     * get请求对象接收
     * @param teacher
     * @param errors
     */
    @GetMapping("/teacher1")
    public void getTeacher1(@Validated Teacher teacher,
                           BindingResult errors) {
        errors.getAllErrors().stream().forEach(error -> {
            System.out.println(error.getDefaultMessage());
        });
    }

    /**
     * 分组参数验证 校验
     */
    @PostMapping("/teachers")
    public void getTeachers(@Validated({First.class}) @RequestBody Teacher teacher,
                           BindingResult errors) {
        System.out.println(teacher.getId());
        errors.getAllErrors().stream().forEach(error -> {
            System.out.println(error.getDefaultMessage());
        });
    }

    /**
     * 分组参数验证 不校验
     */
    @PostMapping("/teachers2")
    public void getTeachers2(@Validated @RequestBody Teacher teacher,
                            BindingResult errors) {
        System.out.println(teacher.getId());
        errors.getAllErrors().stream().forEach(error -> {
            System.out.println(error.getDefaultMessage());
        });
    }
}
