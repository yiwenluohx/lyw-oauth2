package com.lyw.cloud.lyw.oauth2.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: luohx
 * @Date: 2020/7/30 11:23
 * @Version: 1.0
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World.";
    }

    @GetMapping("/exist")
    public String exist() {
        return "又要失业了...";
    }
}
