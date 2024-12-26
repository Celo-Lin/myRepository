package com.juan.adx.channel.config.database;

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
@MapperScan(basePackages = "com.juan.adx.channel.mapper.permission", 
	sqlSessionTemplateRef = "permissionSqlSessionTemplate")
public class PermissionMybatisConfiguration {

	@Autowired
	@Qualifier("permissionDataSource")
	private DataSource permissionDataSource;

	@Bean(name = "permissionSqlSessionFactory")
	public SqlSessionFactory permissionSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(permissionDataSource);
		return bean.getObject();
	}

	@Bean(name = "permissionTransactionManager")
	public DataSourceTransactionManager permissionTransactionManager() {
		return new DataSourceTransactionManager(permissionDataSource);
	}

	@Bean(name = "permissionSqlSessionTemplate")
	public SqlSessionTemplate permissionSqlSessionTemplate(
			@Qualifier("permissionSqlSessionFactory") SqlSessionFactory permissionSqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(permissionSqlSessionFactory);
	}

	//@Bean(name = "adxMapperScannerConfigurer")
	public MapperScannerConfigurer adxMapperScannerConfigurer() {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setSqlSessionFactoryBeanName("permissionSqlSessionFactory");
		configurer.setSqlSessionTemplateBeanName("permissionSqlSessionTemplate");
		configurer.setBasePackage("com.juan.adx.channel.mapper.permission");
		return configurer;
	}
    
}