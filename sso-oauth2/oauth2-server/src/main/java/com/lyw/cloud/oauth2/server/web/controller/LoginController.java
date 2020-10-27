package com.lyw.cloud.oauth2.server.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/10/27 0027 13:54
 */
@Controller
public class LoginController {

    @GetMapping(value = {"/login"})
    public ModelAndView toLogin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = {"/index"})
    public ModelAndView toIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg","This is <b>great!</b>");
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
