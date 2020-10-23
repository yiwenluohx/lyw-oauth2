package com.lyw.cloud.oauth2.jdbcstore.service;

import com.lyw.cloud.oauth2.jdbcstore.entity.dto.OauthClientDetailsDto;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/10/23 0023 17:40
 */
public interface OAuthService {

    OauthClientDetailsDto loadOauthClientDetails(String clientId);

}
