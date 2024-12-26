package com.juan.adx.common.cache;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.juan.adx.common.utils.GZipUtil;

public class RedisCacheUtils {

	protected static final Logger logger = LoggerFactory.getLogger( RedisCacheUtils.class );

	private RedisTemplate redisTemplate;
	
	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public static Object parseCompressUser( byte[] userByte ) {
		Object obj = null;
		if( null != userByte ) {
			try {
				String appInfo = GZipUtil.uncompress( userByte );
				obj = JSONObject.parseObject( appInfo, Object.class );
			} catch( Exception e ) {
				logger.error( String.format( "uncomparse User error![%s]", userByte ) );
			}
		}
		return obj;
	}

	public void pushCacheAndTrim( final String key,List<? extends Object> cache, int seconds ) {
		logger.info( "pushCacheAndTrim redis key:{} has {} items", new Object[] { key, null == cache ? 0 : cache.size() } );

		if( (cache == null || cache.isEmpty()) || StringUtils.isEmpty( key ) ) {
			return;
		}
		final String[] cs = new String[ cache.size() ];
		for( int i = 0; i < cache.size(); i++ ) {
			cs[ i ] = JSON.toJSONString( cache.get( i ) );
		}
		// FIXME 这几个步骤放在一起执行吧
		// 第一步 取得旧的cache的value个数
		int start = redisTemplate.LISTS.llen( key ).intValue();
		// 第二步 将list的多个值 value 插入到列表 key 的表尾(最右边)。
		redisTemplate.LISTS.rpush( key, seconds, cs );
		// 第三步 移除旧的cache
		if( start > 0 ) {
			redisTemplate.LISTS.ltrim( key, start, start + cache.size() - 1 );
		}

	}
	
	public void pushCacheAndTrimString( final String key,List<String> cache, int seconds ) {
		logger.info( "pushCacheAndTrimString redis key:{} has {} items", new Object[] { key, null == cache ? 0 : cache.size() } );

		if( (cache == null || cache.isEmpty()) || StringUtils.isEmpty( key ) ) {
			return;
		}
		final String[] cs = new String[ cache.size() ];
		for( int i = 0; i < cache.size(); i++ ) {
			cs[ i ] = cache.get( i );
		}
		// FIXME 这几个步骤放在一起执行吧
		// 第一步 取得旧的cache的value个数
		int start = redisTemplate.LISTS.llen( key ).intValue();
		// 第二步 将list的多个值 value 插入到列表 key 的表尾(最右边)。
		redisTemplate.LISTS.rpush( key, seconds, cs );
		// 第三步 移除旧的cache
		if( start > 0 ) {
			redisTemplate.LISTS.ltrim( key, start, start + cache.size() - 1 );
		}

	}
	
	public void emptyCache(String key){
		if(redisTemplate.KEYS.exists(key)){
			redisTemplate.KEYS.del(key);
		}
		
	}
	
	public void setCache( final String key, Map<String, Object> cache, int seconds) {
		logger.info( "setCache redis key:{} has {} items", cache.toString() );
		
		if( cache==null || cache.isEmpty() ) {
			return;
		}
		String value = JSON.toJSONString( cache );
		redisTemplate.STRINGS.setEx(key, seconds, value);
	}
	
}
