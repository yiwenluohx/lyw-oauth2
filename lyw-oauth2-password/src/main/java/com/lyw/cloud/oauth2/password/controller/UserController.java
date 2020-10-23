package com.lyw.cloud.oauth2.password.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Author: luohx
 * @Description: 用户信息控制类
 * @Date: 2020/10/22 17:02
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @GetMapping("/userinfo")
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }
}
