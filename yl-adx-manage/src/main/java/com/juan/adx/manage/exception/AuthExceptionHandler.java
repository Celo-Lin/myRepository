package com.juan.adx.manage.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.DefaultOAuth2ExceptionRenderer;
import org.springframework.security.oauth2.provider.error.OAuth2ExceptionRenderer;
import org.springframework.web.context.request.ServletWebRequest;

import com.juan.adx.common.model.ManageResponse;

public class AuthExceptionHandler {

	protected static final Logger logger = LoggerFactory.getLogger( AuthExceptionHandler.class );

	private AuthExceptionTranslator exceptionTranslator = new AuthExceptionTranslator();

	private OAuth2ExceptionRenderer exceptionRenderer = new DefaultOAuth2ExceptionRenderer();

	public void handle( HttpServletRequest request, HttpServletResponse response, Exception exception )
			throws IOException, ServletException {
		try {
			ResponseEntity<ManageResponse> result = exceptionTranslator.translate( exception );
			exceptionRenderer.handleHttpEntityResponse( result, new ServletWebRequest( request, response ) );
			response.flushBuffer();
		} catch( ServletException ex ) {
			throw ex;
		} catch( IOException ex ) {
			throw ex;
		} catch( RuntimeException ex ) {
			throw ex;
		} catch( Exception ex ) {
			throw new RuntimeException( ex );
		}
	}

	public void setExceptionRenderer( OAuth2ExceptionRenderer exceptionRenderer ) {
		this.exceptionRenderer = exceptionRenderer;
	}
}
