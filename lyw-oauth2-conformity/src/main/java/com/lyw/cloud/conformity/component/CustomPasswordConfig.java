package com.lyw.cloud.conformity.component;

import org.springframework.context.annotation.Bean;

/**
 * @Author: luohx
 * @Description: 自定义密码
 * @Date: 2020/12/9 18:43
 */
public class CustomPasswordConfig {
    @Bean
    public CustomPasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }
}
