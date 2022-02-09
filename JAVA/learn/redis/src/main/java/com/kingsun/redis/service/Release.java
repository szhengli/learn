package com.kingsun.redis.service;

import com.kingsun.redis.domain.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class Release {
    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private RedisTemplate redisTemplate;



   public void writeHash(byte[] key, People person){
       HashOperations<byte[], byte[], byte[]> hashOperations = redisTemplate.opsForHash();
       HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();
       Map<byte[], byte[]> map = mapper.toHash(person);

       hashOperations.putAll(map);







   }


    public  void opRedis(){

        template.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Long size = connection.dbSize();
                // Can cast to StringRedisConnection if using a StringRedisTemplate
                ((StringRedisConnection)connection).set("key", "value");
              Map<String, String> all =  ((StringRedisConnection) connection).hGetAll("20220119:wxms");
                all.forEach((String k, String v)->{
                    System.out.println("key: "+ k + " " + "value: " + v);
                });
              return  all;
            }
        });
    }

}
