package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;

/**
 * @auth jian j w
 * @date 2020/4/23 19:13
 * @Description
 */
@Configuration
@PropertySource(value = "classpath:redis.properties", encoding = "utf-8")
@EnableCaching
public class SpringCacheConfig {

    @Bean
    public RedisConnectionFactory getConnectionFactory(@Value("${redis.host}") String host,
                                                       @Value("${redis.password}") String password,
                                                       @Value("${redis.db}") int db,
                                                       @Value("${redis.port}") int port,
                                                       @Value("${redis.minIdle}") int minIdle) {
        JedisConnectionFactory factory = new JedisConnectionFactory();//jedis连接工厂
        factory.setHostName(host);
        factory.setPassword(password);
        factory.setDatabase(db);
        factory.setPort(port);

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();//池对象
        jedisPoolConfig.setMinIdle(minIdle);

        factory.setPoolConfig(jedisPoolConfig);
        return factory;
    }

    @Bean
    public RedisTemplate<Object,Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置key的序列化器为String序列化器
        redisTemplate.setKeySerializer(redisTemplate.getStringSerializer());

        //设置value(value为string)的对象序列化器
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(serializer);

        return redisTemplate;
    }

    @Bean
    public CacheManager getCacheManager(RedisOperations<Object,Object>redisOperations){
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisOperations);
        ArrayList<String> cacheNames = new ArrayList<>();
        cacheNames.add("sysOfficeCache");
        redisCacheManager.setCacheNames(cacheNames);//设置缓存管理器中的缓存对象名
        redisCacheManager.setDefaultExpiration(360);//默认生存时间
        return  redisCacheManager;
    }
}
