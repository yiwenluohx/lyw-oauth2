package com.lyw.cloud.oauth2.server.config;

import com.lyw.cloud.oauth2.server.component.MessagesLocalResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/10/27 0027 14:15
 */
@Configuration
@EnableConfigurationProperties({ WebMvcProperties.class})
public class MyMvcConfig implements WebMvcConfigurer {
    //装载WebMvcProperties 属性
    @Autowired
    WebMvcProperties webMvcProperties;
    /**
     * 自定义LocalResolver
     * @Author nicky.ma
     * @Date 2019/11/24 13:45
     * @return org.springframework.web.servlet.LocaleResolver
     */
    @Bean
    public LocaleResolver  localeResolver(){
        MessagesLocalResolver localResolver = new MessagesLocalResolver();
        localResolver.setDefaultLocale(webMvcProperties.getLocale());
        return localResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/**");;
    }
}
