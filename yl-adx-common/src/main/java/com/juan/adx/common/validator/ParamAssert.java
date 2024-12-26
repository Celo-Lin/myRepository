package com.juan.adx.common.validator;

import org.apache.commons.lang3.StringUtils;

import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;

public class ParamAssert {

	
	/**
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     */
	public static void notBlank(String str, String msg) {
		if(!StringUtils.isNotBlank(str)){
			//抛出异常
			throw new ServiceRuntimeException(ExceptionEnum.INVALID_PARAM.getCode(), msg);
		}
	}
	
	/**
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     */
	public static void isBlank(String str, String msg) {
		if(StringUtils.isBlank(str)){
			//抛出异常
			throw new ServiceRuntimeException(ExceptionEnum.INVALID_PARAM.getCode(), msg);
		}
	}
	
	public static void isTrue( boolean flag, String msg) {
		if( flag ) {
			throw new ServiceRuntimeException(ExceptionEnum.INVALID_PARAM.getCode(), msg);
		}
	}
	
	public static void isFalse( boolean flag, String msg ) {
		if( !flag ) {
			throw new ServiceRuntimeException(ExceptionEnum.INVALID_PARAM.getCode(), msg);
		}
	}

	public static void lessThanZero(Integer num, String msg) {
		if(num <= 0){
			throw new ServiceRuntimeException(ExceptionEnum.INVALID_PARAM.getCode(), msg);
		}
	}
}
