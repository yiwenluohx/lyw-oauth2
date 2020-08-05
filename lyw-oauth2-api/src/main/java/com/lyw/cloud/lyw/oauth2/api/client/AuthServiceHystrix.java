package com.lyw.cloud.lyw.oauth2.api.client;

import com.lyw.cloud.lyw.oauth2.domain.JWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: luohx
 * @Date: 2020/8/5 10:21
 * @Version: 1.0
 */
@Component
@Slf4j
public class AuthServiceHystrix implements AuthServiceClient {
    @Override
    public JWT getToken(String grantType, String client_id, String client_secret, String username, String password) {
                log.error("获取JWT Token失败, grantType: {}, client_id: {}, client_secret: {}, username: {}, password: {}",
                 grantType, client_id, client_secret, username, password);
        return null;
    }
}
