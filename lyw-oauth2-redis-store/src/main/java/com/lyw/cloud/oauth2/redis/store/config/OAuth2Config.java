package com.lyw.cloud.oauth2.redis.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Author: luohx
 * @Description:
 * @Date: 2020/10/26 13:34
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    private static final String CLIENT_ID = "cms";
    private static final String SECRET_CHAR_SEQUENCE = "{noop}secret";
    private static final String SCOPE_READ = "read";
    private static final String SCOPE_WRITE = "write";
    private static final String TRUST = "trust";
    private static final String USER = "user";
    private static final String ALL = "all";
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 2 * 60;
    private static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 2 * 60;
    // 密码模式授权模式
    private static final String GRANT_TYPE_PASSWORD = "password";
    //授权码模式
    private static final String AUTHORIZATION_CODE = "authorization_code";
    //refresh token模式
    private static final String REFRESH_TOKEN = "refresh_token";
    //简化授权模式
    private static final String IMPLICIT = "implicit";
    //指定哪些资源是需要授权验证的
    private static final String RESOURCE_ID = "resource_id";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                // 使用内存存储
                .inMemory()
                //标记客户端id
                .withClient(CLIENT_ID)
                //客户端安全码
                .secret(SECRET_CHAR_SEQUENCE)
                //为true 直接自动授权成功返回code
                .autoApprove(true)
                //重定向uri
                .redirectUris("http://127.0.0.1:8084/cms/login")
                //允许授权范围
                .scopes(ALL)
                //token 时间秒
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                //刷新token 时间 秒
                .refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS)
                //允许授权类型
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT);
    }

    /**
     *  配置token管理
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                //支持获取token方式
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS)
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
    TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

}
