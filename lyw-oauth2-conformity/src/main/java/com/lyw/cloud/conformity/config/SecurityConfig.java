package com.lyw.cloud.conformity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/12/4 0004 15:50
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 授权验证服务
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                //添加内置测试用户
                .withUser("nicky")
                .password("{noop}123")
                .roles("admin");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/asserts/**");
        web.ignoring().antMatchers("/favicon.ico");
    }

    /**
     * HTTP请求处理
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //配置SpringSecurity默认登录页并允许访问
                .formLogin().permitAll()
                //弹框登录页
                //.and().httpBasic()
                ///自定义登录页
                //.and().formLogin().loginPage("/login").permitAll()
                //配置登出页面
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and().authorizeRequests().antMatchers("/oauth/**", "/login/**", "/logout/**").permitAll()
                //其余所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                //关闭跨域保护
                .and().csrf().disable();
    }
}