package com.juan.adx.api.config.cache;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "adx.redis")
public class RedisConfig implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Long getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(Long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}
	

}
