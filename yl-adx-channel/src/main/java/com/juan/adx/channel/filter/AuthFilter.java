package com.juan.adx.channel.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Splitter;
import com.juan.adx.channel.exception.AuthExceptionHandler;
import com.juan.adx.channel.exception.UnauthorizedException;
import com.juan.adx.channel.service.ChannelJwtTokenService;
import com.juan.adx.common.constants.ChannelJwtClaimsKey;
import com.juan.adx.model.entity.sspmanage.ChannelBrowserSession;

import lombok.Setter;

//@Component
//@WebFilter(urlPatterns="/channel/*",filterName="authFilter")
public class AuthFilter implements Filter  {

	static final String HEADER_STRING = "Authorization";
	
    @Setter
    private ChannelJwtTokenService tokenService;
    
	private AuthExceptionHandler authExceptionHandler = new AuthExceptionHandler();

	private List<String> excludedUris;
	
	private static List<String> WITHOUT_PERMISSION = new ArrayList<>();

	
	static {
		WITHOUT_PERMISSION.add("/channel/auth/login");
		WITHOUT_PERMISSION.add(".html");
		WITHOUT_PERMISSION.add(".js");
		WITHOUT_PERMISSION.add(".css");
		WITHOUT_PERMISSION.add(".otf");
		WITHOUT_PERMISSION.add(".ttf");
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String excludedUrls = filterConfig.getInitParameter("exclusions");
		excludedUris = Splitter.on(",").omitEmptyStrings().splitToList(excludedUrls);
	}
	
	
	private boolean isExcludedUri(String uri) {
		if (excludedUris == null || excludedUris.size() <= 0) {
			return false;
		}
		for (String ex : excludedUris) {
			uri = uri.trim();
			ex = ex.trim();
			if (uri.toLowerCase().matches(ex.toLowerCase().replace("*",".*")))
				return true;
		}
		return false;
	}

	@Override
	public void doFilter(ServletRequest rt, ServletResponse rp, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) rt;
		HttpServletResponse response = (HttpServletResponse) rp;
		String requestUri = request.getRequestURI();
		
		if(isExcludedUri(requestUri)) {
			filterChain.doFilter(request,response);
			return;
		}
    	for(String uri : WITHOUT_PERMISSION){
			if(requestUri != null && requestUri.endsWith(uri)){
				filterChain.doFilter(request,response);
				return;
			}
		}
    	
        final String headerToken = request.getHeader(HEADER_STRING);

        if (headerToken == null || headerToken.isEmpty()) {
        	authExceptionHandler.handle( request, response, new UnauthorizedException("Missing or invalid Authorization header") );
        	return;
        }
        
        DecodedJWT decodedJWT = null;
        try {
            //校验 access token合法性
        	decodedJWT = this.tokenService.deAccessToken(headerToken);
        } catch (final Exception e) {
        	authExceptionHandler.handle( request, response, new UnauthorizedException("No authenticated") );
        	return;
        }
    	if(decodedJWT == null){
    		authExceptionHandler.handle( request, response, new UnauthorizedException("No authenticated") );
    		return;
    	}
    	String tokenUserId = decodedJWT.getSubject();
    	if(StringUtils.isBlank(tokenUserId)){
    		authExceptionHandler.handle( request, response, new UnauthorizedException("No authenticated") );
    		return;
    	}
    	//检查token校验码与服务端是否一致
    	boolean isTokenCode = this.tokenService.compareTokenCode(decodedJWT);
    	if(!isTokenCode) {
    		authExceptionHandler.handle( request, response, new UnauthorizedException("token code invalid") );
    		return;
    	}
    	ChannelBrowserSession authSession = new ChannelBrowserSession();
    	authSession.setSession_key_user_id(tokenUserId);
    	authSession.setSession_key_user_name(decodedJWT.getClaims().get(ChannelJwtClaimsKey.userName).asString());
    	authSession.setSession_key_ssp_partner_id(decodedJWT.getClaims().get(ChannelJwtClaimsKey.sspPartnerId).asString());
    	filterChain.doFilter(new RequestWrapper( request, authSession ), response);
    }
	
	@Override
	public void destroy() { }




}
