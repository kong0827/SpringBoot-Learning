package com.kxj.controller;

import com.kxj.annotation.NoRepeatSubmitAnnotation;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/hello")
    @NoRepeatSubmitAnnotation(interval = 100)
    public String hello(@RequestParam String msg) {
        System.out.println("msg = " + msg);
        return "hello";
    }

    @PostMapping("/hello1")
    @NoRepeatSubmitAnnotation(interval = 100)
    public String hello1(@RequestBody Student student) {
        System.out.println("msg = " + student);
        return "hello";
    }
}