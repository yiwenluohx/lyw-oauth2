package com.lyw.cloud.lyw.oauth2.api.client;

import com.lyw.cloud.lyw.oauth2.domain.JWT;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: luohx
 * @Date: 2020/8/5 10:10
 * @Version: 1.0
 */
@FeignClient(value = "lyw-oauth2-auth", fallback = AuthServiceHystrix.class)
public interface AuthServiceClient {
    //    @PostMapping(value = "/oauth/token")
//    JWT getToken(@RequestHeader(value = "Authorization") String authorization,
//                 @RequestParam("grant_type") String grantType,
//                 @RequestParam("username") String username,
//                 @RequestParam("password") String password);
    @PostMapping(value = "/oauth/token")
    JWT getToken(@RequestParam("grant_type") String grantType,
                 @RequestParam("client_id") String client_id,
                 @RequestParam("client_secret") String client_secret,
                 @RequestParam("username") String username,
                 @RequestParam("password") String password);
}
