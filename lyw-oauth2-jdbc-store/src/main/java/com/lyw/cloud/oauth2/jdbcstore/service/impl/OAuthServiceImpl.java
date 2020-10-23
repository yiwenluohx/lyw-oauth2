package com.lyw.cloud.oauth2.jdbcstore.service.impl;

import com.lyw.cloud.oauth2.jdbcstore.entity.dto.OauthClientDetailsDto;
import com.lyw.cloud.oauth2.jdbcstore.mapper.OAuthDao;
import com.lyw.cloud.oauth2.jdbcstore.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/10/23 0023 17:41
 */
@Service
public class OAuthServiceImpl implements OAuthService {

    @Autowired
    OAuthDao oAuthDao;

    @Override
    @Transactional(rollbackFor = Exception.class,readOnly = true)
    public OauthClientDetailsDto loadOauthClientDetails(String clientId) {
        return oAuthDao.loadOauthClientDetailsByClientId(clientId);
    }


}
