package com.lyw.cloud.conformity.config;

import com.lyw.cloud.conformity.component.JdbcTokenStoreUserApprovalHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
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
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/12/4 15:40
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource(name = "userDetailService")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 客户端详情相关配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }
    /**
     * 认证服务端点配置类
     * 将开发者定义的AuthorizationServerConfigurer实现一一应用到AuthorizationServerSecurityConfigurer，
     * 对应AuthorizationServerConfigurer接口的configure(ClientDetailsServiceConfigurer clients)方法
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //使用内存保存生成的token
        endpoints
                .tokenStore(tokenStore())
                //为password模式和code模式提供AuthenticationManager
                .authenticationManager(authenticationManager)
                //刷新令牌授权将包含对用户详细信息的检查，确保账户仍然活动，设置userDetailsService
                .userDetailsService(userDetailsService)
                //储存授权码（redis、db、内存）
                .authorizationCodeServices(authorizationCodeServices())
                //设置userApprovalHandler
                .userApprovalHandler(userApprovalHandler())
                //支持获取token方式
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST,HttpMethod.PUT,HttpMethod.DELETE,HttpMethod.OPTIONS)
                //刷新token
                .reuseRefreshTokens(true);
    }

    /**
     * 认证服务配置安全策略（配置/oauth/token的安全策略、/oauth/authorize端点也需要安全，但是此端点只是普通的和用户UI同级别安全配置，无需在此处配置）
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 开启/oauth/token_key验证端口认证权限访问 放开token_key、check_token
        security.tokenKeyAccess("isAuthenticated()")
                // 开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("isAuthenticated()")
                //允许表单认证
                .allowFormAuthenticationForClients();
    }

    /**
            *  自定义JdbcClientDetailsServiceBuilder
     */
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

    /**
     *  自定义JdbcTokenStore
     */
    @Bean
    public TokenStore jdbcTokenStore() {
        Assert.state(dataSource != null, "DataSource must be provided");
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    public OAuth2RequestFactory oAuth2RequestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService());
    }

    /**
     *  解决未保存用户的授权信息，导致每次登陆都要重新授权
     */
    @Bean
    public UserApprovalHandler userApprovalHandler() {
        JdbcTokenStoreUserApprovalHandler approvalHandler = new JdbcTokenStoreUserApprovalHandler();
        approvalHandler.setTokenStore(jdbcTokenStore());
        approvalHandler.setClientDetailsService(clientDetailsService());
        approvalHandler.setRequestFactory(oAuth2RequestFactory());
        return approvalHandler;
    }

}
