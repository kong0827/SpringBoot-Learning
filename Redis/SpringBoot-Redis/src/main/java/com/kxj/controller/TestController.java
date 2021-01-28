package com.kxj.controller;

import cn.hutool.core.lang.Dict;
import com.kxj.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiangjin.kong
 * @date 2021/1/28 10:53
 */
@RestController
@Slf4j
public class TestController {

    @RateLimiter(value = 5)
    @GetMapping("/test1")
    public Dict test1() {
        log.info("[test1]被执行了。。。。。。。。。");
        return Dict.create().set("msg", "hello world").set("description", "别想一直看到我，不信你刷新试试看");
    }

    @GetMapping("/test2")
    public Dict test2() {
        log.info("【test2】被执行了。。。。。");
        return Dict.create().set("msg", "hello,world!").set("description", "我一直都在，卟离卟弃");
    }

    @RateLimiter(value = 2, key = "测试自定义key")
    @GetMapping("/test3")
    public Dict test3() {
        log.info("【test3】被执行了。。。。。");
        return Dict.create().set("msg", "hello,world!").set("description", "别想一直看到我，不信你快速刷新看看~");
    }
}
