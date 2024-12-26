package com.juan.adx.api.config.exception;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.juan.adx.common.exception.ExceptionCode;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.WebResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
	
	private final static String SERVICE_MSG = "service exception";
	
	private final static String SYSTEM_MSG = "Internal Server Errorn";
	
    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity<WebResponse> agentException(HttpServletRequest request, ServiceRuntimeException exception) {
    	String msg = exception.getMessage() == null ? SERVICE_MSG : exception.getMessage();
        return ResponseEntity.status(exception.getStatusCode())
                .body(new WebResponse(exception.getCode(), msg));
    }
    
    
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<WebResponse> duplicateKeyException(Exception exception) {
    	log.error("sql DuplicateKeyException. ",exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new WebResponse(ExceptionCode.CommonCode.DATABASE_ERROR, "索引重复异常"));
    }
    
    
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<WebResponse> sqlException(Exception exception) {
    	log.error("system SQLException. ",exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new WebResponse(ExceptionCode.CommonCode.DATABASE_ERROR, "sql error"));
    }
    
    
    @ExceptionHandler(BindException.class)
    public ResponseEntity<WebResponse> paramBindException(Exception exception) {
    	log.error("param BindException, 参数异常. ", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new WebResponse(ExceptionCode.CommonCode.INVALID_PARAM, "参数异常"));
    }
    
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<WebResponse> messageConversionException(HttpServletRequest request, Exception exception) {
    	log.error("message conversion HttpMessageNotReadableException, 请求参数JSON格式错误. msg: {}",exception.getMessage());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    			.body(new WebResponse(ExceptionCode.CommonCode.INVALID_PARAM, "请求参数JSON格式错误"));
    }
    

    @ExceptionHandler(Exception.class)
	public ResponseEntity<WebResponse> allExceptionHandel(HttpServletRequest request, Exception e) {
    	log.error("system unkown exception. ",e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body(new WebResponse(ExceptionCode.UNCLASSIFIED, SYSTEM_MSG));
	}
    
}
