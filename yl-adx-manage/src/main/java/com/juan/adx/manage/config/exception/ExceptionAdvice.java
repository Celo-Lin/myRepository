package com.juan.adx.manage.config.exception;

import java.sql.SQLException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    	log.error("system MethodArgumentNotValidException. ",exception);
//        BindingResult bindingResult = exception.getBindingResult();
//        List<ObjectError> allErrors = bindingResult.getAllErrors();
//        StringBuilder stringBuilder = new StringBuilder();
//        allErrors.forEach(objectError -> stringBuilder.append(objectError.getDefaultMessage()).append(";"));
//        String msg = stringBuilder.toString();
    	String msg = exception.getFieldError().getDefaultMessage();
        msg = StringUtils.isBlank(msg) ? "参数异常" : msg;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new WebResponse(ExceptionCode.CommonCode.INVALID_PARAM, msg));
    }  
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse> handleConstraintViolationException(ConstraintViolationException exception) {
    	log.error("system ConstraintViolationException. ",exception);
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        StringBuilder stringBuilder = new StringBuilder();
        violations.forEach(violation -> stringBuilder.append(violation.getMessage()).append(";"));
        String msg = stringBuilder.toString();
        msg = StringUtils.isBlank(msg) ? "参数异常" : msg;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new WebResponse(ExceptionCode.CommonCode.INVALID_PARAM, msg));
    }
    
    @ExceptionHandler(BindException.class)
    public ResponseEntity<WebResponse> paramBindException(BindException exception) {
    	log.error("param BindException. ", exception);
    	String msg = exception.getFieldError().getDefaultMessage();
    	msg = StringUtils.isBlank(msg) ? "参数异常" : msg;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new WebResponse(ExceptionCode.CommonCode.INVALID_PARAM, msg));
    }
    

    @ExceptionHandler(Exception.class)
	public ResponseEntity<WebResponse> allExceptionHandel(HttpServletRequest request, Exception exception) {
    	log.error("system unkown exception. ",exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body(new WebResponse(ExceptionCode.UNCLASSIFIED, SYSTEM_MSG));
	}
    
    
}
