package com.runoob.cas.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 赵秀非 E-mail:zhaoxiufei@gmail.com
 * @version 创建时间：2018-04-20 10:26
 */
@Component
@Data
//@ConfigurationProperties(prefix = "spring.cas.security")
public class CasSecurityProperties {
    /**
     * cas 服务器地址
     */
    @Value("${cas.server.host.url}")
    private String serverUrl;

    @Value("${cas.server.host.login_url}")
    private String serverLoginUrl;

    @Value("${cas.server.host.logout_url}")
    private String serverLogoutUrl;

    @Value("${app.server.host.url}")
    private String applicationUrl;

    @Value("${app.login.url}")
    private String applicationLoginUrl;

    @Value("${app.logout.url}")
    private String applicationLogoutUrl;
}
