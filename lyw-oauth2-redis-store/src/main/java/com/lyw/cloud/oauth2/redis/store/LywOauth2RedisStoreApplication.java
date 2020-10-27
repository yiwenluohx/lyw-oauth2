package com.lyw.cloud.oauth2.redis.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 对OAuth2.0的令牌、授权码等进行redis存储
 */
@SpringBootApplication
public class LywOauth2RedisStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(LywOauth2RedisStoreApplication.class, args);
    }

}
