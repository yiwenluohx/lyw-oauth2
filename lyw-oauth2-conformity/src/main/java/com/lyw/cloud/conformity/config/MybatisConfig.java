package com.lyw.cloud.conformity.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: luohx
 * @Description: Mybatis配置
 * @Date: 2020/12/4 17:14
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.lyw.cloud.conformity.mapper"})
public class MybatisConfig {
    /**
     * 配置支持驼峰命名和大小写自动转换
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer(){
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }


}