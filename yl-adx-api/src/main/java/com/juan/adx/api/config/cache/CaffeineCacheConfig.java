package com.juan.adx.api.config.cache;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableCaching
@Configuration
public class CaffeineCacheConfig {
	
	public final static String globalCacheName = "globalCache" ;
	
	public final static String slotsCacheName = "slots";
	
	//缓存过期时间
	public final static int caffeineCacheExpires = 10;
	
	//初始的缓存容量大小
	public final static int initialCapacity = 200;
	
	//缓存的最大条数
	public final static int maximumSize = 5000;
	

	@Primary
	@Bean("cacheManager")
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager();
		//根据名字可以创建多个cache，但是多个cache使用相同的策略
		cacheManager.setCacheNames(Arrays.asList(globalCacheName, slotsCacheName)); 
		cacheManager.setCaffeine(caffeineCacheBuilder());
		//是否允许缓存value为null
		cacheManager.setAllowNullValues(true);
		//cacheManager.setCacheLoader(cacheLoader());
		return cacheManager;
	}
	
	
	private Caffeine<Object, Object> caffeineCacheBuilder() {
		return Caffeine.newBuilder()
				//初始的缓存容量大小
				.initialCapacity(initialCapacity)
				//缓存的最大条数,maximumSize和maximumWeight不可以同时使用
				.maximumSize(maximumSize)
				//最后一次写入或访问后经过固定时间过期,expireAfterWrite和expireAfterAccess同时存在时，以expireAfterWrite为准
				//.expireAfterAccess(10, TimeUnit.SECONDS)
				//最后一次写入经过固定时间过期
				.expireAfterWrite(caffeineCacheExpires, TimeUnit.MINUTES)
		
				//创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存,需要设置cacheLoader
                //.refreshAfterWrite(1, TimeUnit.SECONDS)
				//打开key的弱引用,weakValues和softValues不可以同时使用
				//.weakKeys()
				//统计功能
				.recordStats()
				;
	}
	
	 /**
     * 必须要指定这个Bean，refreshAfterWrite配置属性才生效
     */
    @Bean
    public CacheLoader<Object, Object> cacheLoader() {
        return new CacheLoader<Object, Object>() {
            @Override
            public Object load(Object key) throws Exception { return null;}
            // 重写这个方法将oldValue值返回回去，进而刷新缓存
            @Override
            public Object reload(Object key, Object oldValue) throws Exception {
            	log.info("refresh key: {} | oldValue: {}", key, oldValue);
                return oldValue;
            }
        };
    }
}