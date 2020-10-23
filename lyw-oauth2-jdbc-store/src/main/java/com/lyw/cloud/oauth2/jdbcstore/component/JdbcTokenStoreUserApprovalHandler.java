package com.lyw.cloud.oauth2.jdbcstore.component;

import com.lyw.cloud.oauth2.jdbcstore.entity.dto.OauthClientDetailsDto;
import com.lyw.cloud.oauth2.jdbcstore.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;

/**
 * @Author: luohx
 * @Description: 自定义TokenStoreUserApprovalHandler
 * @Date: 2020/10/23 18:33
 */
public class JdbcTokenStoreUserApprovalHandler extends TokenStoreUserApprovalHandler {
    @Autowired
    private OAuthService oAuthService;

    /**
     * 查询表中数据判断是否是符合要求客户端
     */
    @Override
    public boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
        if (super.isApproved(authorizationRequest, userAuthentication)) {
            return true;
        }
        if (!userAuthentication.isAuthenticated()) {
            return false;
        }
        String clientId = authorizationRequest.getClientId();
        OauthClientDetailsDto clientDetails = oAuthService.loadOauthClientDetails(clientId);
        return clientDetails != null;
    }
}
