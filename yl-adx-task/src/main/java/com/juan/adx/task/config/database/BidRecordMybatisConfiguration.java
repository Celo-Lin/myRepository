package com.juan.adx.task.config.database;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.juan.adx.task.mapper.bidrecord", 
	sqlSessionTemplateRef = "bidRecordSqlSessionTemplate")
public class BidRecordMybatisConfiguration {

	@Autowired
	@Qualifier("shardingBidRecordDataSource")
	private DataSource shardingBidRecordDataSource;

	@Bean(name = "bidRecordSqlSessionFactory")
	public SqlSessionFactory bidRecordSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(shardingBidRecordDataSource);
		return bean.getObject();
	}

	@Bean(name = "bidRecordTransactionManager")
	public DataSourceTransactionManager adxTransactionManager() {
		return new DataSourceTransactionManager(shardingBidRecordDataSource);
	}

	@Bean(name = "bidRecordSqlSessionTemplate")
	public SqlSessionTemplate bidRecordSqlSessionTemplate(
			@Qualifier("bidRecordSqlSessionFactory") SqlSessionFactory bidRecordSqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(bidRecordSqlSessionFactory);
	}

	//@Bean(name = "adxMapperScannerConfigurer")
	public MapperScannerConfigurer adxMapperScannerConfigurer() {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setSqlSessionFactoryBeanName("bidRecordSqlSessionFactory");
		configurer.setSqlSessionTemplateBeanName("bidRecordSqlSessionTemplate");
		configurer.setBasePackage("com.juan.adx.task.mapper.bidrecord");
		return configurer;
	}
    
}