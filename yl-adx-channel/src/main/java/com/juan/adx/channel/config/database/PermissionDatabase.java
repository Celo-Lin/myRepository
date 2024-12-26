package com.juan.adx.channel.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PermissionDatabase {

	@Value("${spring.datasource.permission.mysql-db.jdbc-url}")
	private String jdbcUrl;
	
	@Value("${spring.datasource.permission.mysql-db.username}")
	private String userName;
	
	@Value("${spring.datasource.permission.mysql-db.password}")
	private String password;
	
}
