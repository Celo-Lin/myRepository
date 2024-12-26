package com.juan.adx.channel.config.redis;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.juan.adx.common.cache.RedisCacheUtils;
import com.juan.adx.common.cache.RedisTemplate;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {

	@Autowired
	private RedisConfig redisConfig;

	@Bean("jedisPool")
    public JedisPool redisPoolFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(redisConfig.getMaxIdle());
		poolConfig.setMaxTotal(redisConfig.getMaxTotal());
		//在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true。则得到的jedis实例肯定是可以用的。
		poolConfig.setTestOnBorrow(true);
		//在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。
		poolConfig.setTestOnReturn(true);
		poolConfig.setMaxWait(Duration.ofMillis(redisConfig.getMaxWaitMillis()));
		//连接耗尽的时候，是否阻塞，false 会抛出异常，true 阻塞直到超时。默认为true。
		poolConfig.setBlockWhenExhausted(false);
		
        JedisPool jedisPool = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
        		redisConfig.getTimeout(), redisConfig.getPassword());
        return jedisPool;
    }

	@Bean("redisTemplate")
	public RedisTemplate redisTemplateFactory() {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setJedisPool(redisPoolFactory());
		redisTemplate.init();
		return redisTemplate;
	}

	@Bean(name = "redisUtil")
	public RedisCacheUtils redisCacheUtils(RedisTemplate redisTemplate) {
		RedisCacheUtils redisCacheUtils = new RedisCacheUtils();
		redisCacheUtils.setRedisTemplate(redisTemplate);
		return redisCacheUtils;
	}

}