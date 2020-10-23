package com.lyw.cloud.oauth2.jdbcstore.config;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: luohx
 * @Description:  Mybatis配置
 * @Date: 2020/10/23  10:42
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.lyw.cloud.oauth2.jdbcstore.mapper"})
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
