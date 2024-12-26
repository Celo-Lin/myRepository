package com.juan.adx.channel.exception;

import com.juan.adx.common.exception.ExceptionCode;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 8576035560657585864L;

	public UnauthorizedException( String msg, Throwable t ) {
		super( msg, t );
	}

	public UnauthorizedException( String msg ) {
		super( msg );
	}

	public int getErrorCode() {
		return ExceptionCode.CommonCode.UNAUTHORIZED;
	}

	public int getHttpErrorCode() {
		return 401;
	}
}
