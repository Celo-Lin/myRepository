package com.juan.adx.task.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DruidDataSourceConfig {

	/**
	 * 	初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
	 * 	默认值：5
	 */
	@Value("${spring.datasource.mysql-db.initial.size:5}")
	private int initialSize;

	/**
	 * 	最大连接池数量
	 * 	默认值：20
	 */
	@Value("${spring.datasource.mysql-db.max.active:20}")
	private int maxActive;
	
	/**
	 * 	最小连接池数量
	 * 	默认值：10
	 */
	@Value("${spring.datasource.mysql-db.min.idle:10}")
	private int minIdle;
	
	/**
	 * 	单位：毫秒
	 * 	获取连接时最大等待时间。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，
	 * 	如果需要可以通过配置useUnfairLock属性为true使用非公平锁
	 * 	默认值：60000
	 */
	@Value("${spring.datasource.mysql-db.max.wait:60000}")
	private int maxWait;
	
	
	/**
	 * 	用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。
	 * 	如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
	 * 	默认值：select 1
	 */
	@Value("${spring.datasource.mysql-db.validation.query:select 1}")
	private String validationQuery;
	
	/**
	 * 	建议配置为true，不影响性能，并且保证安全性。
	 * 	申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
	 * 	默认值：true
	 */
	@Value("${spring.datasource.mysql-db.test.while.idle:true}")
	private boolean testWhileIdle;
	
	/**
	 * 	申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
	 * 	默认值：false
	 */
	@Value("${spring.datasource.mysql-db.test.on.borrow:false}")
	private boolean testOnBorrow;
	
	/**
	 * 	归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
	 * 	默认值：false
	 */
	@Value("${spring.datasource.mysql-db.test.on.return:false}")
	private boolean testOnReturn;
	
	/**
	 * 	单位：毫秒
	 * 	有两个含义：
	 * 	1) Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接。
	 * 	2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
	 * 	默认值：2000 
	 */
	@Value("${spring.datasource.mysql-db.time.between.eviction.runs.millis:2000}")
	private long timeBetweenEvictionRunsMillis;
	
	/**
	 * 	单位：毫秒
	 * 	连接保持空闲而不被驱逐的最小时间
	 * 	默认值：600000 【10分钟】
	 */
	@Value("${spring.datasource.mysql-db.min.evictable.idle.time.millis:600000}")
	private long minEvictableIdleTimeMillis;
	
	/**
	 * 	单位：毫秒
	 * 	连接保持空闲而不被驱逐的最大时间
	 * 	默认值：900000 【15分钟】
	 */
	@Value("${spring.datasource.mysql-db.max.evictable.idle.time.millis:900000}")
	private long maxEvictableIdleTimeMillis;
	
	/**
	 * 	打开后，增强timeBetweenEvictionRunsMillis的周期性连接检查，minIdle内的空闲连接，每次检查强制验证连接有效性. 
	 * 	参考：https://github.com/alibaba/druid/wiki/KeepAlive_cn
	 * 	默认值：true
	 */
	@Value("${spring.datasource.mysql-db.keep.alive:true}")
	private boolean keepAlive;
	
	/**
	 * 	用于负载均衡在SLB后面连接的多个数据库
	 */
	@Value("${spring.datasource.mysql-db.phy.max.use.count:100000}")
	private long phyMaxUseCount;
	
	/**
	 * 	连接泄露检查，打开removeAbandoned功能 。连接从连接池借出后，长时间不归还，将触发强制回连接。
	 * 	回收周期随timeBetweenEvictionRunsMillis进行，如果连接为从连接池借出状态，并且未执行任何sql，并且从借出时间起已超过removeAbandonedTimeout时间，则强制归还连接到连接池中。
	 * 	默认值：true
	 */
	@Value("${spring.datasource.mysql-db.remove.abandoned:false}")
	private boolean removeAbandoned;
	
	/**
	 * 	超时时间，秒
	 * 	默认值：2分钟
	 */
	@Value("${spring.datasource.mysql-db.remove.abandoned.timeout:120}")
	private int removeAbandonedTimeout;
	
	/**
	 * 	关闭abanded连接时输出错误日志，这样出现连接泄露时可以通过错误日志定位忘记关闭连接的位置
	 * 	默认值：true
	 */
	@Value("${spring.datasource.mysql-db.log.abandoned:true}")
	private boolean logAbandoned;
}
