package com.juan.adx.api.config.database;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.juan.adx.api.mapper", 
	sqlSessionTemplateRef = "adxSqlSessionTemplate")
public class AdxMybatisConfiguration {

	@Autowired
	@Qualifier("adxDataSource")
	private DataSource adxDataSource;

	@Bean(name = "adxSqlSessionFactory")
	public SqlSessionFactory adxSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(adxDataSource);
		return bean.getObject();
	}

	@Bean(name = "adxTransactionManager")
	public DataSourceTransactionManager adxTransactionManager() {
		return new DataSourceTransactionManager(adxDataSource);
	}

	@Bean(name = "adxSqlSessionTemplate")
	public SqlSessionTemplate adxSqlSessionTemplate(
			@Qualifier("adxSqlSessionFactory") SqlSessionFactory dspSqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(dspSqlSessionFactory);
	}

}