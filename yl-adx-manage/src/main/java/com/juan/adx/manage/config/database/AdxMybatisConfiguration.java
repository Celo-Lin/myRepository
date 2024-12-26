package com.juan.adx.manage.config.database;

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
@MapperScan(basePackages = "com.juan.adx.manage.mapper.adx", 
	sqlSessionTemplateRef = "adxSqlSessionTemplate")
public class AdxMybatisConfiguration {

	@Autowired
	@Qualifier("shardingDspDataSource")
	private DataSource shardingDspDataSource;

	@Bean(name = "adxSqlSessionFactory")
	public SqlSessionFactory adxSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(shardingDspDataSource);
		return bean.getObject();
	}

	@Bean(name = "adxTransactionManager")
	public DataSourceTransactionManager adxTransactionManager() {
		return new DataSourceTransactionManager(shardingDspDataSource);
	}

	@Bean(name = "adxSqlSessionTemplate")
	public SqlSessionTemplate adxSqlSessionTemplate(
			@Qualifier("adxSqlSessionFactory") SqlSessionFactory adxSqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(adxSqlSessionFactory);
	}

	//@Bean(name = "adxMapperScannerConfigurer")
	public MapperScannerConfigurer adxMapperScannerConfigurer() {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setSqlSessionFactoryBeanName("adxSqlSessionFactory");
		configurer.setSqlSessionTemplateBeanName("adxSqlSessionTemplate");
		configurer.setBasePackage("com.juan.adx.manage.mapper.adx");
		return configurer;
	}
    
}