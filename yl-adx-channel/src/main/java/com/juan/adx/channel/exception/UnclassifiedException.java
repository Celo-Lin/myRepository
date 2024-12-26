package com.juan.adx.channel.exception;

import com.juan.adx.common.exception.ExceptionCode;

public class UnclassifiedException extends RuntimeException {

	private static final long serialVersionUID = 6007458425402319852L;

	public UnclassifiedException( String msg, Throwable t ) {
		super( msg, t );
	}

	public UnclassifiedException( String msg ) {
		super( msg );
	}

	public int getErrorCode() {
		return ExceptionCode.UNCLASSIFIED;
	}

	public int getHttpErrorCode() {
		return 500;
	}
}
