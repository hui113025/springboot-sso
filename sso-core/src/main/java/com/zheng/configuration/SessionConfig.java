package com.zheng.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * Created by zhenghui on 2018/1/23.
 */
@Configuration
public class SessionConfig {
    @Bean
    public CookieHttpSessionStrategy cookieHttpSessionStrategy() {
        CookieHttpSessionStrategy strategy = new CookieHttpSessionStrategy();
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookieName("SSOSESSIONID");//cookies名称
        cookieSerializer.setCookieMaxAge(1800);//过期时间(秒)
        //如果是域名需设置一级域名
//        cookieSerializer.setDomainName("zheng.com");
        strategy.setCookieSerializer(cookieSerializer);
        return strategy;
    }
}
