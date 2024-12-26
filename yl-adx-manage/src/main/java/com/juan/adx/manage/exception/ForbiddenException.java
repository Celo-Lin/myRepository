package com.juan.adx.manage.exception;

import com.juan.adx.common.exception.ExceptionCode;

public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 5015502969349307188L;

	public ForbiddenException( String msg, Throwable t ) {
		super( msg, t );
	}

	public ForbiddenException( String msg ) {
		super( msg );
	}

	public int getErrorCode() {
		return ExceptionCode.CommonCode.FORBIDDEN;
	}

	public int getHttpErrorCode() {
		return 403;
	}
}