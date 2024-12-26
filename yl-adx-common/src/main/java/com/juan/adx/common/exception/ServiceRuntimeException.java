package com.juan.adx.common.exception;

public class ServiceRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 8565818520991978408L;

	private Integer code;

    private Integer statusCode;

    private String message;

    private Exception exception;

    public ServiceRuntimeException() {
        super();
    }

    public ServiceRuntimeException(Integer code, String message) {
        this.code = code;
        this.statusCode = 200;
        this.message = message;
    }

    public ServiceRuntimeException(Integer code, Integer statusCode, String message) {
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
    }

    public ServiceRuntimeException(Integer code, Integer statusCode, String message, Exception exception) {
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
        this.exception = exception;
    }


    public ServiceRuntimeException(ExceptionEnum enums) {
        this.code = enums.getCode();
        this.statusCode = enums.getStatusCode();
        this.message = enums.getMessage();
    }

    public ServiceRuntimeException(ExceptionEnum enums, Exception exception) {
        this.code = enums.getCode();
        this.statusCode = enums.getStatusCode();
        this.message = enums.getMessage();
        this.exception = exception;
    }

    public ServiceRuntimeException(String message) {
        super(message);
        this.message=message;
    }

    public ServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
		this.message=message;
    }

    public ServiceRuntimeException(Throwable cause) {
        super(cause);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
