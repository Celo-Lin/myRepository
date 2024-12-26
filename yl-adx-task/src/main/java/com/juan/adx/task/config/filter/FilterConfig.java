package com.juan.adx.task.config.filter;

import java.util.Map;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Maps;
import com.juan.adx.task.filter.XssFilter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<XssFilter> xssFilter(){
        FilterRegistrationBean<XssFilter> filterRegistrationBean = new FilterRegistrationBean<XssFilter>();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("xssFilter");
        Map<String, String> initParameters = Maps.newHashMap();
        initParameters.put("excludes", "*.js,*.gif,*.jpg,*.png,*.css,*.ico");
        initParameters.put("isIncludeRichText", "false");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }

}
