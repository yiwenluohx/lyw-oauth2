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
 * @Author: luohx  密码前面都加了{noop}是JDBC这种查询方式默认的加密算法
 * @Description: AuthorizationServerConfigurerAdapter只是一个提供给开发配置ClientDetailsServiceConfigurer、AuthorizationServerEndpointsConfigurer、
 * AuthorizationServerSecurityConfigurer空壳类并没有持有以上三个配置Bean对象.由初始化时调用AuthorizationServerConfigurerAdapter.configure(xxxConfigure) 给机会开发注入、配置
 * @Date: 2020/10/23  17:53
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

    /**
     * 主要是注入ClientDetailsService实例对象（唯一配置注入）。其它地方可以通过ClientDetailsServiceConfigurer调用开发配置的ClientDetailsService。
     * 系统提供的二个ClientDetailsService实现类：JdbcClientDetailsService、InMemoryClientDetailsService
     * 初始化ClientDetailsServiceBuilder
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置数据从oauth_client_details表读取来存储
        clients.withClientDetails(clientDetailsService());
    }

    /**
     *  AuthorizationServerEndpointsConfigurer其实是一个装载类，装载Endpoints所有相关的类配置（AuthorizationServer、TokenServices、TokenStore、ClientDetailsService、UserDetailsService
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(jdbcTokenStore())
                //配置令牌生成jwt转换器
                //.accessTokenConverter(accessTokenConverter())
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
     * Spring Security安全配置提供给AuthorizationServer去配置AuthorizationServer的端点（/oauth/****）的安全访问规则、过滤器Filter
     * *  配置：安全检查流程,用来配置令牌端点（Token Endpoint）的安全与权限访问
     * 	 *  默认过滤器：BasicAuthenticationFilter
     * 	 *  1、oauth_client_details表中clientSecret字段加密【ClientDetails属性secret】
     * 	 *  2、CheckEndpoint类的接口 oauth/check_token 无需经过过滤器过滤，默认值：denyAll()
     * 	 * 对以下的几个端点进行权限配置：
     * 	 * /oauth/authorize：授权端点
     * 	 * /oauth/token：令牌端点
     * 	 * /oauth/confirm_access：用户确认授权提交端点
     * 	 * /oauth/error：授权服务错误信息端点
     * 	 * /oauth/check_token：用于资源服务访问的令牌解析端点
     * 	 * /oauth/token_key：提供公有密匙的端点，如果使用JWT令牌的话
     *  用来配置令牌端点(Token Endpoint)的安全约束
     *
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
