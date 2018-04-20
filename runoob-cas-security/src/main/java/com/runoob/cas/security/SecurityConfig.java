package com.runoob.cas.security;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.web.cors.CorsUtils;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2018-04-19 11:41
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CasSecurityProperties casProperties;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(casAuthenticationProvider());
    }

    /**
     * 定义安全策略
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();

        http.csrf().disable();

        http.authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/static/**").permitAll() // 不拦截静态资源
                .antMatchers("/api/**").permitAll()  // 不拦截对外API
                .anyRequest().authenticated();  // 所有资源都需要登陆后才可以访问。

        http.logout().permitAll();  // 不拦截注销

        http.exceptionHandling()
                .authenticationEntryPoint(casAuthenticationEntryPoint());
//        .defaultAuthenticationEntryPointFor(new AjaxAuthenticationEntryPoint(), new AjaxRequestMatcher());
        http.addFilter(casAuthenticationFilter())
                .addFilterBefore(casLogoutFilter(), LogoutFilter.class)
                // 单点注销的过滤器，必须配置在SpringSecurity的过滤器链中，如果直接配置在Web容器中，貌似是不起作用的。我自己的是不起作用的。
                .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class);
//        http.antMatcher("/**");
//        http.addFilterBefore(new MyFilter(), FilterSecurityInterceptor.class);
    }

    /**
     * 单点退出监听器
     *
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> servletListenerRegistrationBean =
                new ServletListenerRegistrationBean<>();
        servletListenerRegistrationBean.setListener(new SingleSignOutHttpSessionListener());
        return servletListenerRegistrationBean;
    }

    /**
     * 认证的入口
     */
    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl(casProperties.getServerLoginUrl());
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
        return casAuthenticationEntryPoint;
    }

    /**
     * 指定service相关信息
     */
    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        // 设置回调的service路径，此为主页路径
        serviceProperties.setService(casProperties.getApplicationUrl() + casProperties.getApplicationLoginUrl());
        // 对所有的未拥有ticket的访问均需要验证
        serviceProperties.setAuthenticateAllArtifacts(true);
        return serviceProperties;
    }

    /**
     * CAS认证过滤器
     */
    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        casAuthenticationFilter.setFilterProcessesUrl(casProperties.getApplicationLoginUrl());
        return casAuthenticationFilter;
    }

//    @Bean
//    public SessionAuthenticationStrategy sessionStrategy() {
//        return new SessionFixationProtectionStrategy();
//    }
//
//    @Bean
//    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
//        return new SecurityEvaluationContextExtension();
//    }
    /**
     * cas 认证 Provider
     */
    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setAuthenticationUserDetailsService(customUserDetailsService());
        //casAuthenticationProvider.setUserDetailsService(customUserDetailsService()); //这里只是接口类型，实现的接口不一样，都可以的。
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
        casAuthenticationProvider.setKey("casAuthenticationProviderKey");
        return casAuthenticationProvider;
    }

    /*@Bean
    public UserDetailsService customUserDetailsService(){
        return new CustomUserDetailsService();
    }*/

    /**
     * 用户自定义的AuthenticationUserDetailsService
     */
    @Bean
    public AuthenticationUserDetailsService<CasAssertionAuthenticationToken> customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
        return new Cas20ServiceTicketValidator(casProperties.getServerUrl());
    }

    /**
     * 单点登出过滤器
     */
    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setCasServerUrlPrefix(casProperties.getServerUrl());
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }

    /**
     * 请求单点退出过滤器
     */
    @Bean
    public LogoutFilter casLogoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter(casProperties.getServerLogoutUrl(), new SecurityContextLogoutHandler());
        logoutFilter.setFilterProcessesUrl(casProperties.getApplicationLogoutUrl());
        return logoutFilter;
    }
}
