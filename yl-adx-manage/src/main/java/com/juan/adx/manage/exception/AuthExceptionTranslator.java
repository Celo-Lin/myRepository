package com.juan.adx.manage.exception;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableAnalyzer;

import com.juan.adx.common.exception.ExceptionCode;
import com.juan.adx.common.model.ManageResponse;

public class AuthExceptionTranslator {

	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

	public ResponseEntity<ManageResponse> translate( Exception ex ) throws Exception {
		Throwable[] causeChain = throwableAnalyzer.determineCauseChain( ex );
		RuntimeException ase = (RuntimeException) throwableAnalyzer.getFirstThrowableOfType(RuntimeException.class, causeChain );
		if( null != ase ) {
			return handleOAuth2Exception( ase );
		}
		return handleOAuth2Exception( new UnclassifiedException( "Internal Server Error", ex ) );
	}

	private ResponseEntity<ManageResponse> handleOAuth2Exception( RuntimeException ex ) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl( "no-cache" );
		headers.setPragma( "no-cache" );
		headers.setExpires( 0 );
		headers.setContentType( MediaType.APPLICATION_JSON );

		int code = ExceptionCode.UNCLASSIFIED;
		HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
		if( ex instanceof UnauthorizedException ) {
			UnauthorizedException unauthorizedException = (UnauthorizedException) ex;
			httpStatus = HttpStatus.valueOf(unauthorizedException.getHttpErrorCode());
			code = unauthorizedException.getErrorCode();
		} else if( ex instanceof ForbiddenException ) {
			ForbiddenException forbiddenException = (ForbiddenException) ex;
			httpStatus = HttpStatus.valueOf(forbiddenException.getHttpErrorCode());
			code = forbiddenException.getErrorCode();
		} else if( ex instanceof UnclassifiedException ) {
			UnclassifiedException unclassifiedException = (UnclassifiedException) ex;
			httpStatus = HttpStatus.valueOf(unclassifiedException.getHttpErrorCode());
			code = unclassifiedException.getErrorCode();
		}
		ManageResponse response = new ManageResponse( code, ex.getMessage() );
		return new ResponseEntity<ManageResponse>(response, headers, httpStatus);
	}

	public void setThrowableAnalyzer( ThrowableAnalyzer throwableAnalyzer ) {
		this.throwableAnalyzer = throwableAnalyzer;
	}
}