package kxj.controller;

import kxj.entity.User;
import org.springframework.web.bind.annotation.*;

/**
 * @author kxj
 * @date 2019/12/27 0:06
 * @desc
 */
@RestController
public class UserController {

    @GetMapping(value = "/user/{id}")
    public User getInfo(@PathVariable(name = "id") String id) {
        throw new RuntimeException("user not exist");
    }


}
