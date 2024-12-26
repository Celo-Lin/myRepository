package com.juan.adx.common.cache;

public interface RedisKeyExpireTime {

	/**
	 * 永不过期
	 */
	int DAY_EVER = -1;
	
	/**
	 * 过期时间 300天
	 */
	int DAY_300 = 25920000;
	
	/**
	 * 过期时间180天
	 */
	int DAY_180 = 15552000;
	
	/**
	 * 过期时间 90天
	 */
	int DAY_90 = 7776000;
	
	/**
	 * 过期时间 60天
	 */
	int DAY_60 = 5184000;
	
	/**
	 * 过期时间 30天
	 */
	int DAY_30 = 2592000;
	
	/**
     * 过期时间 15天
     */
    int DAY_15 = 1296000;
	
	/**
	 * 过期时间7天
	 */
	int DAY_7 = 604800;

	/**
	 * 过期时间2天
	 */
	int DAY_2 = 172800;
	
	/**
	 * 过期时间1天
	 */
	int DAY_1 = 86400;
	
	
	
	/**
	 * 过期时间1小时
	 */
	int HOUR_1 = 3600;
	
	/**
	 * 过期时间2小时
	 */
	int HOUR_2 = 7200;
	
	/**
	 * 过期时间3小时
	 */
	int HOUR_3 = 10800;
	
	/**
	 * 过期时间4小时
	 */
	int HOUR_4 = 14400;
	
	/**
	 * 过期时间5小时
	 */
	int HOUR_5 = 18000;
	
	
	
	/**
	 * 过期时间1分钟
	 */
	int MINUTE_1 = 60;
	
	/**
	 * 过期时间2分钟
	 */
	int MINUTE_2 = 120;

	/**
	 * 过期时间2分钟
	 */
	int MINUTE_5 = 300;

	/**
	 * 过期时间10分钟
	 */
	int MINUTE_10 = 600;
	
	/**
	 * 过期时间20分钟
	 */
	int MINUTE_20 = 1200;
	
	/**
	 * 过期时间10分又10秒钟
	 */
	int MINUTE_10_10 = 610;
	
	/**
	 * 过期时间11分钟
	 */
	int MINUTE_11 = 660;
	
	
	
	/**
	 * 过期时间11秒种
	 */
	int SECOND_11 = 11;
	
	/**
	 * 过期时间10秒种
	 */
	int SECOND_10 = 10;
	
	
}
