package com.kxj.controller;

import com.kxj.annotation.DistributeAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kxj
 * @date 2022/6/20 00:47
 * @desc
 */
@RestController
public class TestController {

    @GetMapping("/student")
    @DistributeAnnotation(param = "id")
    // 参数必须是对象
    public void getStudents(Student student) {

    }


    @GetMapping("/student1")
    @DistributeAnnotation
    public void getStudents1(Student student) {

    }


    @GetMapping("/student2")
    @DistributeAnnotation(argNum = 1)
    // 参数必须是对象
    public void getStudents(String lock) {

    }}
