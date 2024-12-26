package com.juan.adx.channel.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class AdxDatabase {

	@Value("${spring.datasource.adx.mysql-db.jdbc-url}")
	private String jdbcUrl;
	
	@Value("${spring.datasource.adx.mysql-db.username}")
	private String userName;
	
	@Value("${spring.datasource.adx.mysql-db.password}")
	private String password;
	
}
