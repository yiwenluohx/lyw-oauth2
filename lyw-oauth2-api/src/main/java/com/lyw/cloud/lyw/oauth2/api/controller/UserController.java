package com.lyw.cloud.lyw.oauth2.api.controller;

import com.lyw.cloud.lyw.oauth2.api.service.UserService;
import com.lyw.cloud.lyw.oauth2.domain.User;
import com.lyw.cloud.lyw.oauth2.domain.user.response.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: luohx
 * @Description: 用户注册、登录信息接口
 * @Date: 2020/7/30 11:24
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController{

//    @Autowired
//    private LoginUserHolder loginUserHolder;
//
//    @GetMapping("/currentUser")
//    public UserDTO currentUser() {
//        return loginUserHolder.getCurrentUser();
//    }

    @Autowired
    private UserService userDetailService;

    @PostMapping("/register")
    public User postUser(@RequestParam("username") String username,
                         @RequestParam("password") String password) {
        return userDetailService.insertUser(username, password);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDTO> login(@RequestParam("username") String username,
                                              @RequestParam("password") String password) {
        UserLoginDTO dto = userDetailService.login(username, password);
        return ResponseEntity.ok(dto);
    }
}
