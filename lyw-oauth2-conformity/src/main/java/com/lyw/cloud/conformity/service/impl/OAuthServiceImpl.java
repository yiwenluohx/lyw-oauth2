package com.lyw.cloud.conformity.service.impl;

import com.lyw.cloud.conformity.entity.dto.OauthClientDetailsDto;
import com.lyw.cloud.conformity.mapper.OAuthDao;
import com.lyw.cloud.conformity.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/12/4 17:11
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