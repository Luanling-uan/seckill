package com.sziit.user.controller;

import com.sziit.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // 校验用户名和密码
        if ("test".equals(username) && "123456".equals(password)) {
            return "token-" + username;
        }
        return "用户名或密码错误";
    }


    @GetMapping("/info")
    public String info(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("token-")) {
            return "用户信息: " + token.substring(6);
        }
        return "未登录";
    }
} 