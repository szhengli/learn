package com.kingsun.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class RedisRelease {
    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("release_master_1")
                .sentinel("192.168.1.32", 17020)
                .sentinel("192.168.1.33", 17020)
                .sentinel("192.168.1.34", 17020);

        return new JedisConnectionFactory(sentinelConfig);
    }

}
