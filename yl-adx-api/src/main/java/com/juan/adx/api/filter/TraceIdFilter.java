package com.juan.adx.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.juan.adx.api.context.TraceContext;
import com.juan.adx.common.utils.SnowFlake;

public class TraceIdFilter extends OncePerRequestFilter {
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	
        try {
        	TraceContext assertionContext = new TraceContext();
        	String traceId = String.valueOf(SnowFlake.getInstance().nextId());
        	assertionContext.setTraceId(traceId);
        	TraceContext.initContext(assertionContext);
        	
        	filterChain.doFilter(request, response);
        } finally {
            //在请求结束后清除上下文
        	TraceContext.resetContext();
        }
    }
	
}