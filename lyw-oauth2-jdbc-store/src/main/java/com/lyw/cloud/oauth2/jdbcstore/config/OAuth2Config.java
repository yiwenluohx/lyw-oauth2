package com.lyw.cloud.oauth2.jdbcstore.config;

import com.lyw.cloud.oauth2.jdbcstore.component.JdbcTokenStoreUserApprovalHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/10/23 0023 17:53
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Resource(name = "userDetailService")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置数据从oauth_client_details表读取来存储
        clients.withClientDetails(clientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //保存token
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(jdbcTokenStore())
                //设置userDetailsService
                .userDetailsService(userDetailsService)
                //储存授权码
                .authorizationCodeServices(authorizationCodeServices())
                //设置userApprovalHandler
                .userApprovalHandler(userApprovalHandler())
                //支持获取token方式
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST,HttpMethod.PUT,HttpMethod.DELETE,HttpMethod.OPTIONS)
                //刷新token
                .reuseRefreshTokens(true);
    }

    /**
     * 认证服务器的安全配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 开启/oauth/token_key验证端口认证权限访问
                .tokenKeyAccess("isAuthenticated()")
                //  开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("isAuthenticated()")
                //允许表单认证 在授权码模式下会导致无法根据code获取token　
                .allowFormAuthenticationForClients();
    }

    @Bean
    public ClientDetailsService clientDetailsService() {
        Assert.state(dataSource != null, "DataSource must be provided");
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        Assert.state(dataSource != null, "DataSource must be provided");
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public TokenStore jdbcTokenStore() {
        Assert.state(dataSource != null, "DataSource must be provided");
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public OAuth2RequestFactory oAuth2RequestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService());
    }

    @Bean
    public UserApprovalHandler userApprovalHandler() {
        JdbcTokenStoreUserApprovalHandler approvalHandler = new JdbcTokenStoreUserApprovalHandler();
        approvalHandler.setTokenStore(jdbcTokenStore());
        approvalHandler.setClientDetailsService(clientDetailsService());
        approvalHandler.setRequestFactory(oAuth2RequestFactory());
        return approvalHandler;
    }
}
