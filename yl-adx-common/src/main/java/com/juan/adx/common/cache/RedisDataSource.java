package com.juan.adx.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDataSource {
	
	private final Logger logger = LoggerFactory.getLogger(RedisDataSource.class);
	
	private JedisPool jedisPool;
	 
    public Jedis getRedisClient() {
        Jedis jedis = null;
        try {
        	jedis = jedisPool.getResource();
            return jedis;
        } catch (Exception e) {
        	logger.error("getRedisClent error:", e);
            if (null != jedis){
            	jedis.close();
            }
        }
        return null;
    }
    
    public void returnResource(Jedis jedis) {
    	jedis.close();
    }

}
