package com.juan.adx.manage.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.juan.adx.common.model.CustomUserDetails;
import org.apache.commons.lang3.StringUtils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Splitter;
import com.juan.adx.manage.exception.AuthExceptionHandler;
import com.juan.adx.manage.exception.ForbiddenException;
import com.juan.adx.manage.exception.UnauthorizedException;
import com.juan.adx.manage.service.permission.JwtTokenService;
import com.juan.adx.manage.service.permission.UserRoleService;
import com.juan.adx.common.constants.JwtClaimsKey;
import com.juan.adx.model.entity.permission.BrowserSession;

import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

//@Component
//@WebFilter(urlPatterns="/manage/*",filterName="authFilter")
public class AuthFilter implements Filter  {

	static final String HEADER_STRING = "Authorization";

    @Setter
    private JwtTokenService tokenService;

    @Setter
    private UserRoleService grantService;

	private AuthExceptionHandler authExceptionHandler = new AuthExceptionHandler();

	private List<String> excludedUris;

	private static List<String> WITHOUT_PERMISSION = new ArrayList<>();


	static {
		WITHOUT_PERMISSION.add("/permission/auth/login");
		WITHOUT_PERMISSION.add("/permission/user/update_passwd");
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
    	String tokenUid = decodedJWT.getSubject();
    	if(StringUtils.isBlank(tokenUid)){
    		authExceptionHandler.handle( request, response, new UnauthorizedException("No authenticated") );
    		return;
    	}
    	//检查token校验码与服务端是否一致
    	boolean isTokenCode = this.tokenService.compareTokenCode(decodedJWT);
    	if(!isTokenCode) {
    		authExceptionHandler.handle( request, response, new UnauthorizedException("token code invalid") );
    		return;
    	}
    	boolean isAuth = grantService.auth(tokenUid, requestUri );
    	if(!isAuth) {
    		authExceptionHandler.handle( request, response, new ForbiddenException("没有访问权限，请联系管理员！") );
    		return;
    	}
    	BrowserSession authSession = new BrowserSession();
    	authSession.setUserId(tokenUid);
    	authSession.setUserName(decodedJWT.getClaims().get(JwtClaimsKey.userName).asString());
    	authSession.setRoleIds(decodedJWT.getClaims().get(JwtClaimsKey.roleIds).asString());
		// 将 authSession 设置到 SecurityContextHolder 中
		CustomUserDetails userDetails = new CustomUserDetails(
				authSession.getUserId(),
				authSession.getUserName(),
				authSession.getRoleIds(),
				Arrays.stream(authSession.getRoleIds().split(","))
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList())
		);

		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
    	filterChain.doFilter(new RequestWrapper( request, authSession ), response);
    }

	@Override
	public void destroy() { }




}
