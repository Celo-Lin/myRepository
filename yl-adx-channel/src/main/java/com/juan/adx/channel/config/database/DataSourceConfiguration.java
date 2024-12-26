package com.juan.adx.channel.config.database;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DataSourceConfiguration {
	
	private final static String driverClass = "com.mysql.cj.jdbc.Driver";

	@Autowired
	private DruidDataSourceConfig druidConfig;

	@Autowired
	private AdxDatabase adxDatabase;
	
	@Autowired
	private PermissionDatabase permissionDatabase;
	
	
    @Bean(name = "adxDataSource")
    public DataSource adxDataSource() {
    	DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
    	dataSource.setDriverClassName(driverClass);
    	dataSource.setUrl(adxDatabase.getJdbcUrl());
    	dataSource.setUsername(adxDatabase.getUserName());
    	dataSource.setPassword(adxDatabase.getPassword());
    	initDruidConfig(dataSource);
    	return dataSource;
    }
    
    @Bean(name = "permissionDataSource")
    public DataSource permissionDataSource() {
    	DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
    	dataSource.setDriverClassName(driverClass);
    	dataSource.setUrl(permissionDatabase.getJdbcUrl());
    	dataSource.setUsername(permissionDatabase.getUserName());
    	dataSource.setPassword(permissionDatabase.getPassword());
    	initDruidConfig(dataSource);
    	return dataSource;
    }

    

    public void initDruidConfig(DruidDataSource dataSource) {
    	
    	dataSource.setInitialSize(druidConfig.getInitialSize());
    	dataSource.setMinIdle(druidConfig.getMinIdle());
    	dataSource.setMaxActive(druidConfig.getMaxActive());
    	dataSource.setMaxWait(druidConfig.getMaxWait());
    	
    	//配置间隔多久才进行一次检测,检测需要关闭的空闲连接,单位是毫秒
    	dataSource.setTimeBetweenEvictionRunsMillis(druidConfig.getTimeBetweenEvictionRunsMillis());
    	//配置一个连接在池中最小生存的时间,单位是毫秒
    	dataSource.setMinEvictableIdleTimeMillis(druidConfig.getMinEvictableIdleTimeMillis());
    	dataSource.setMaxEvictableIdleTimeMillis(druidConfig.getMaxEvictableIdleTimeMillis());
    	
    	dataSource.setValidationQuery(druidConfig.getValidationQuery());
    	dataSource.setTestWhileIdle(druidConfig.isTestWhileIdle());
    	dataSource.setTestOnBorrow(druidConfig.isTestOnBorrow());
    	dataSource.setTestOnReturn(druidConfig.isTestOnReturn());
    	
    	dataSource.setKeepAlive(druidConfig.isKeepAlive());
    	
    	dataSource.setRemoveAbandoned(druidConfig.isRemoveAbandoned());
    	dataSource.setRemoveAbandonedTimeout(druidConfig.getRemoveAbandonedTimeout());
    	dataSource.setLogAbandoned(druidConfig.isLogAbandoned());
    }
    

    
    /**
     * 	注册一个StatViewServlet
	@Bean
     
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet(){
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(),"/druid/*");
        //白名单： allow 为空时代表允许所有访问
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny","192.168.1.73");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername","root");
        servletRegistrationBean.addInitParameter("loginPassword","password");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable","false");// 禁用HTML页面上的“Reset All”功能
        return servletRegistrationBean;
    }*/

    /**
     * 	注册一个：filterRegistrationBean
    @Bean
    
    public FilterRegistrationBean<WebStatFilter> druidStatFilter(){

        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>(new WebStatFilter());
        filterRegistrationBean.setName("druidWebStatFilter");
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }*/

}
