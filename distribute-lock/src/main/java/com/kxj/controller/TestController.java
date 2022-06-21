package com.kxj.controller;

import com.kxj.annotation.DistributeAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kxj
 * @date 2022/6/20 00:47
 * @desc
 */
@RestController
public class TestController {

    @PostMapping("/student")
    @DistributeAnnotation(param = "id")
    // 参数必须是对象
    public void getStudents(@RequestBody Student student) {

    }


    @PostMapping("/student1")
    @DistributeAnnotation
    public void getStudents1(@RequestBody Student student) {

    }


    @GetMapping("/student2")
    @DistributeAnnotation(argNum = 1)
    // 参数必须是对象
    public void getStudents(String lock) {

    }}
