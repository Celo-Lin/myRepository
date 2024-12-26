package com.juan.adx.api.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.juan.adx.api.filter.DecompressFilter;
import com.juan.adx.api.filter.TraceIdFilter;

@Configuration
public class FilterConfig {
	

    @Bean
    public FilterRegistrationBean<DecompressFilter> decompressFilter(){
    	FilterRegistrationBean<DecompressFilter> filterRegistrationBean = new FilterRegistrationBean<DecompressFilter>();
    	DecompressFilter decompressFilter = new DecompressFilter();
    	filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico");
    	filterRegistrationBean.setFilter(decompressFilter);
    	filterRegistrationBean.setOrder(2);
    	filterRegistrationBean.setEnabled(true);
    	filterRegistrationBean.addUrlPatterns("/ssp/ads");
    	filterRegistrationBean.setName("decompressFilter");
    	return filterRegistrationBean;
    }
    
    @Bean
    public FilterRegistrationBean<TraceIdFilter> traceIdFilter(){
    	FilterRegistrationBean<TraceIdFilter> filterRegistrationBean = new FilterRegistrationBean<TraceIdFilter>();
    	TraceIdFilter traceIdFilter = new TraceIdFilter();
    	filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico");
    	filterRegistrationBean.setFilter(traceIdFilter);
    	filterRegistrationBean.setOrder(2);
    	filterRegistrationBean.setEnabled(true);
    	filterRegistrationBean.addUrlPatterns("/*");
    	filterRegistrationBean.setName("traceIdFilter");
    	return filterRegistrationBean;
    }
}
