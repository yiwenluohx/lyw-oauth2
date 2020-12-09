package com.lyw.cloud.conformity.config;

import com.lyw.cloud.conformity.component.CustomPasswordConfig;
import com.lyw.cloud.conformity.component.DefaultPasswordConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/12/9 0009 18:48
 */
public class PasswordEncoderConfig {
    @Configuration
    @ConditionalOnProperty(prefix = "platform.oauth2.token.store", name = "type", havingValue = "default")
    @Import(DefaultPasswordConfig.class)
    public class AuthJwtTokenConfig {
    }

    @Configuration
    @ConditionalOnProperty(prefix = "platform.oauth2.token.store", name = "type", havingValue = "custom")
    @Import(CustomPasswordConfig.class)
    public class ResJwtTokenConfig {
    }
}
