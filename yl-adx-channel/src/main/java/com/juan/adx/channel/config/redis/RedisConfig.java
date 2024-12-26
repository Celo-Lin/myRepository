package com.juan.adx.channel.config.redis;

import java.io.Serializable;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "adx.redis")
public class RedisConfig implements Serializable{
 
	private static final long serialVersionUID = 6592630770803412277L;

	/** Redis服务器地址 	*/
	private String host;
	
	/** Redis服务器连接端口 */
	private int port;
	
	/** Redis服务器连接密码（默认为空  */
	private String password;
	
	/**	连接超时时间（毫秒）	 */
	private Integer timeout;
	
	/** 连接池最大连接数（使用负值表示没有限制）	*/
	private Integer maxTotal;
	
	/**	连接池中的最大空闲连接	 */
	private Integer maxIdle;
	
	/**	连接池中的最小空闲连接	 */
	private Integer minIdle;
	
	/** 连接池最大阻塞等待时间（使用负值表示没有限制）*/
	private Long maxWaitMillis;
	
	/** 在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的	*/
	private boolean testOnBorrow;
	
	/** 在return给pool时，是否提前进行validate操作	*/
	private boolean testOnReturn;
	
	/** 如果为true，表示有一个idle object evitor线程对idle object进行扫描，如果validate失败，此object会被从pool中drop掉；
	 * 	这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义	*/
	private boolean testWhileIdle;

}
