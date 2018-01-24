package com.zheng.configuration.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by chenqi on 2017/10/16.
 */
@ConfigurationProperties(prefix = "spring.session.redis")
@Component
public class SessionRedis extends RedisConfigBean {
}
