package com.kxj.controller;

import com.kxj.entity.Student;
import com.kxj.entity.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author xiangjin.kong
 * @date 2021/1/27 14:22
 */
@RestController
public class DateGetController {

    @GetMapping("/date/v1")
    public User dateTimeApiV1(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @RequestParam("localdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localdate,
            @RequestParam("localdatetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localdatetime) {

        System.out.println(date);
        System.out.println(localdate);
        System.out.println(localdatetime);
        return new User("张三", LocalDateTime.now());
    }

    @GetMapping("/date/v2")
    public User dateTimeApiV2(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam("localdate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localdate,
            @RequestParam("localdatetime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime localdatetime) {
        System.out.println(date);
        System.out.println(localdate);
        System.out.println(localdatetime);
        return new User("张三", LocalDateTime.now());
    }

    @GetMapping("/date/v3")
    public User dateTimeApiV3(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam("localdate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localdate,
            @RequestParam("localdatetime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime localdatetime) {
        System.out.println(date);
        System.out.println(localdate);
        System.out.println(localdatetime);
        return new User("张三", LocalDateTime.now());
    }

    @GetMapping("/date/v4")
    public User dateTimeApiV4(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") Date date) {
        System.out.println(date);
        return new User("张三", LocalDateTime.now());
    }


    /**
     * 全局配置返回日期的类型
     * @return
     */
    @GetMapping("/student")
    public Student returnFormat() {
        return new Student("张三", LocalDateTime.now(), new Date());
    }
}
