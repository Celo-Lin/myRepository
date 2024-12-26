package com.juan.adx.channel.config.database;

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


/** 
 *  @Cacheable 注解参数：
    value、cacheNames：两个等同的参数（cacheNames为Spring 4新增，作为value的别名），用于指定缓存存储的集合名。由于Spring 4中新增了@CacheConfig，因此在Spring 3中原本必须有的value属性，也成为非必需项了
    key：缓存对象存储在Map集合中的key值，非必需，缺省按照函数的所有参数组合作为key值，若自己配置需使用SpEL表达式，比如：@Cacheable(key = “#p0”)：使用函数第一个参数作为缓存的key值
    condition：缓存对象的条件，非必需，也需使用SpEL表达式，该条件是在函数被调用之前做判断的，只有满足表达式条件的内容才会被缓存，比如：@Cacheable(key = “#p0”, condition = “#p0.length() < 3”)，表示只有当第一个参数的长度小于3的时候才会被缓存
    unless：另外一个缓存条件参数，非必需，需使用SpEL表达式。它不同于condition参数的地方在于它的判断时机，该条件是在函数被调用之后才做判断的，在符合unless的情况下，不进行缓存。所以它可以通过对result进行判断。
    keyGenerator：用于指定key生成器，非必需。若需要指定一个自定义的key生成器，我们需要去实现org.springframework.cache.interceptor.KeyGenerator接口，并使用该参数来指定。需要注意的是：该参数与key是互斥的
    cacheManager：用于指定使用哪个缓存管理器，非必需。只有当有多个时才需要使用
    cacheResolver：用于指定使用那个缓存解析器，非必需。需通过org.springframework.cache.interceptor.CacheResolver接口来实现自己的缓存解析器，并用该参数指定。
*/
@EnableCaching
@Configuration
public class CaffeineCacheConfig {
    
    public final static String globalCacheName = "globalCache" ;
    
    public final static String slotsCacheName = "slots";

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
                .initialCapacity(200)
                //缓存的最大条数,maximumSize和maximumWeight不可以同时使用
                .maximumSize(500)
                //最后一次写入或访问后经过固定时间过期,expireAfterWrite和expireAfterAccess同时存在时，以expireAfterWrite为准
                //.expireAfterAccess(10, TimeUnit.SECONDS)
                //最后一次写入经过固定时间过期
                .expireAfterWrite(10, TimeUnit.MINUTES)
        
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
                System.out.println("--refresh--:"+key);
                return oldValue;
            }
        };
    }
}
