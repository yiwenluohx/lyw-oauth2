package com.lyw.cloud.conformity.config;

import com.lyw.cloud.conformity.component.AuthDbTokenStore;
import com.lyw.cloud.conformity.component.AuthJwtTokenStore;
import com.lyw.cloud.conformity.component.AuthRedisTokenStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author: luohx
 * @Description: token存储配置
 * @Date: 2020/12/17 21:50
 */
public class TokenStoreConfig {

    @Configuration
    @ConditionalOnProperty(prefix = "platform.oauth2.token.store", name = "type", havingValue = "db")
    @Import(AuthDbTokenStore.class)
    public class JdbcTokenConfig {
    }

    @Configuration
    @ConditionalOnProperty(prefix = "mall.oauth2.token.store", name = "type", havingValue = "redis")
    @Import(AuthRedisTokenStore.class)
    public class RedisTokenConfig {
    }

    @Configuration
    @ConditionalOnProperty(prefix = "platform.oauth2.token.store", name = "type", havingValue = "authJwt")
    @Import(AuthJwtTokenStore.class)
    public class AuthJwtTokenConfig {
    }
}
