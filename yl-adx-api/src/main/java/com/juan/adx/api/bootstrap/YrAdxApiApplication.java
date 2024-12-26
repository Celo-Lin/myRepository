package com.juan.adx.api.bootstrap;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = {"com.juan.adx.api"},
exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, MybatisAutoConfiguration.class})
public class YrAdxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YrAdxApiApplication.class, args);
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        JettyServletWebServerFactory jetty = new JettyServletWebServerFactory();
        return jetty;
    }
    
}
