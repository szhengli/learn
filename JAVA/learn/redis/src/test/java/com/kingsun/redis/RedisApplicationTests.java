package com.kingsun.redis;

import com.kingsun.redis.domain.People;
import com.kingsun.redis.service.Release;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisSentinelConnection;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    private Release release;

    @Test
    void contextLoads() {

        People james = new People();
        james.setFirstName("sunny");
        james.setLastName("zheng");

        release.writeHash("s001".getBytes(StandardCharsets.UTF_8), james);





    }

}
