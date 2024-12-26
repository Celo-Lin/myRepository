package com.juan.adx.manage.config.filter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Maps;
import com.juan.adx.manage.filter.AuthFilter;
import com.juan.adx.manage.filter.XssFilter;
import com.juan.adx.manage.service.permission.JwtTokenService;
import com.juan.adx.manage.service.permission.UserRoleService;

@Configuration
public class FilterConfig {

    @Autowired
    private UserRoleService grantService;
    
    @Autowired
    private JwtTokenService tokenService;

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

    @Bean
    public FilterRegistrationBean<AuthFilter> grantFilterFilter(){
        FilterRegistrationBean<AuthFilter> filterRegistrationBean = new FilterRegistrationBean<AuthFilter>();
        AuthFilter authFilter = new AuthFilter();
        authFilter.setTokenService(tokenService);
        authFilter.setGrantService(grantService);
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico");
        filterRegistrationBean.setFilter(authFilter);
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("authFilter");
        return filterRegistrationBean;
    }
}
