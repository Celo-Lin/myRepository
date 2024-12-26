package com.juan.adx.channel.bootstrap;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.juan.adx.channel"},
exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class, 
		DataSourceTransactionManagerAutoConfiguration.class, MybatisAutoConfiguration.class})
public class YrAdxChannelApplication {

    public static void main(String[] args) {
        SpringApplication.run(YrAdxChannelApplication.class, args);
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        JettyServletWebServerFactory jetty = new JettyServletWebServerFactory();
        return jetty;
    }
    
}
