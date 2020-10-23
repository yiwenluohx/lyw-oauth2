package com.lyw.cloud.lyw.oauth2.api.client;

import com.lyw.cloud.lyw.oauth2.domain.JWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: luohx
 * @Date: 2020/8/7 11:08
 * @Version: 1.0
 */
@Component
@Slf4j
public class AuthServiceHystrix implements AuthServiceClient {
    @Override
    public JWT getToken(String grantType, String clientId, String clientSecret, String username, String password) {
        log.error("获取JWT Token失败, grantType: {}, clientId: {}, clientSecret: {}, username: {}, password: {}",
                grantType, clientId, clientSecret, username, password);
        return null;
    }
//    @Override
//    public JWT getToken(String authorization, String grantType, String username, String password) {
//        log.error("获取JWT Token失败, authorization: {}, grantType: {}, username: {}, password: {}",
//                authorization, grantType, username, password);
//        return null;
//    }
}
