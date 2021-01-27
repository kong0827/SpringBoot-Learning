package com.kxj.controller;

import com.kxj.entity.Student;
import com.kxj.entity.Teacher;
import com.kxj.entity.User;
import org.apache.tomcat.jni.Local;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author xiangjin.kong
 * @date 2021/1/27 13:44
 */
@RestController
public class DatePostController {

    @PostMapping
    public User postMapping() {
        return new User("张三", LocalDateTime.now());
    }

    @PostMapping("/user")
    public Teacher postMapping(@RequestBody Teacher teacher) {
        System.out.println(teacher.getBirthDay());
        System.out.println(teacher.getDate());
        return teacher;
    }

    /**
     * 全局配置返回日期的类型
     * @return
     */
    @PostMapping("/student")
    public Student returnFormat() {
        Student student = new Student("张三", LocalDateTime.now(), new Date());
        return student;
    }
}
