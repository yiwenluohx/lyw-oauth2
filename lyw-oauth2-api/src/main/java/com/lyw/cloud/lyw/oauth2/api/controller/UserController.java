package com.lyw.cloud.lyw.oauth2.api.controller;

import com.lyw.cloud.lyw.oauth2.api.domain.UserDTO;
import com.lyw.cloud.lyw.oauth2.api.holder.LoginUserHolder;
import com.lyw.cloud.lyw.oauth2.api.service.UserService;
import com.lyw.cloud.lyw.oauth2.domain.User;
import com.lyw.cloud.lyw.oauth2.domain.user.response.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: luohx
 * @Description: 获取登录用户信息接口
 * @Date: 2020/7/30 11:24
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LoginUserHolder loginUserHolder;

    @Autowired
    private UserService userService;

    @GetMapping("/currentUser")
    public UserDTO currentUser() {
        return loginUserHolder.getCurrentUser();
    }

    @PostMapping("/register")
    public User postUser(@RequestParam("username") String username,
                         @RequestParam("password") String password) {
        return userService.insertUser(username, password);
    }

    @PostMapping("/login")
    public UserLoginDTO login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
//        return userDetailService.login(username, password);
        return null;
    }

}
