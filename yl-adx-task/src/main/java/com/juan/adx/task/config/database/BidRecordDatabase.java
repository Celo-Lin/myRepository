package com.juan.adx.task.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class BidRecordDatabase {

	@Value("${spring.datasource.bidrecord.mysql-db.jdbc-url}")
	private String jdbcUrl;
	
	@Value("${spring.datasource.bidrecord.mysql-db.username}")
	private String userName;
	
	@Value("${spring.datasource.bidrecord.mysql-db.password}")
	private String password;
	
}
