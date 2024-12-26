package com.juan.adx.api.context;

import lombok.Getter;
import lombok.Setter;

public class TraceContext {
	
	private static final ThreadLocal<TraceContext> contextHolder = new ThreadLocal<TraceContext>();

	private static final ThreadLocal<TraceContext> inheritableContextHolder = new InheritableThreadLocal<TraceContext>();

	@Getter
	@Setter
	private String traceId;

	public static void initContext( TraceContext context ) {
		initContext( context, false );
	}

	public static void initContext( TraceContext context, boolean inheritable ) {
		if( null == context ) {
			resetContext();
		} else {
			if( inheritable ) {
				inheritableContextHolder.set( context );
				contextHolder.remove();
			} else {
				contextHolder.set( context );
				inheritableContextHolder.remove();
			}
		}
	}

	public static TraceContext getContext() {
		TraceContext context = contextHolder.get();
		if( null == context ) {
			context = inheritableContextHolder.get();
		}
		return context;
	}

	public static void resetContext() {
		TraceContext context = getContext();
		if( null != context ) {
			context.traceId = null;
		}
		contextHolder.remove();
		inheritableContextHolder.remove();
	}

	public static String getTraceIdByContext() {
		TraceContext context = contextHolder.get();
		if( null == context ) {
			context = inheritableContextHolder.get();
		}
		if( null == context ) {
			return "";
		}
		return context.getTraceId();
	}
	

}
