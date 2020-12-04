package com.lyw.cloud.conformity.service;

import com.lyw.cloud.conformity.entity.dto.OauthClientDetailsDto;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/12/4 0004 17:10
 */
public interface OAuthService {

    OauthClientDetailsDto loadOauthClientDetails(String clientId);

}