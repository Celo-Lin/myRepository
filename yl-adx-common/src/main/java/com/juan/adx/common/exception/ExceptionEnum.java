package com.juan.adx.common.exception;

public enum ExceptionEnum {

	/**
	 * 成功
	 */
	SUCCESS(ExceptionCode.SUCCESS, 200, "success"),

	/**
	 * 通用异常
	 */
	UNCLASSIFIED(ExceptionCode.UNCLASSIFIED, 200, "unclassified error"),

	/**
	 * db错误
	 */
	DATABASE_ERROR(ExceptionCode.CommonCode.DATABASE_ERROR, 200, "db error"),

	/**
	 * 无效参数
	 */
	INVALID_PARAM(ExceptionCode.CommonCode.INVALID_PARAM, 200, "invalid param"),
	
	/**
	 * 无效签名
	 */
	SIGN_INVALID(ExceptionCode.CommonCode.SIGN_INVALID, 200, "invalid sign"),

	/**
	 * 没有经过身份验证
	 */
	UNAUTHORIZED(ExceptionCode.CommonCode.UNAUTHORIZED, 200, "No authenticated"),
	
	/**
	 * 广告位没有配置预算，请联系对接人处理
	 */
	NOT_CONFIG_BUDGET(ExceptionCode.AdxApiCode.NOT_CONFIG_BUDGET, 200, "广告位未分配预算，请联系对接人处理!"),
	
	/**
	 * 广告位ID无效
	 */
	INVALID_SLOT_ID(ExceptionCode.AdxApiCode.INVALID_SLOT_ID, 200, "广告位ID无效"),
	
	/**
	 * 广告位已超过单日最大请求限制
	 */
	DSP_REQUEST_EXCEED_MAX_LIMIT(ExceptionCode.AdxApiCode.DSP_REQUEST_EXCEED_MAX_LIMIT, 200, "广告位已超过单日最大请求限制"),
	
	/**
	 * 设备已超过单日最大请求限制
	 */
	DSP_DEVICE_REQUEST_EXCEED_MAX_LIMIT(ExceptionCode.AdxApiCode.DSP_DEVICE_REQUEST_EXCEED_MAX_LIMIT, 200, "设备已超过单日最大请求限制"),
	
	/**
	 * 广告位配置错误（没有配置DSP映射）
	 */
	NOT_CONFIG_DSP_MAPPING(ExceptionCode.AdxApiCode.NOT_CONFIG_DSP_MAPPING, 200, "广告位配置错误，请联系对接人处理!"),
	
	/**
	 * 没有可以填充的广告
	 */
	ADVERT_NOT_FILL(ExceptionCode.AdxApiCode.ADVERT_NOT_FILL, 200, "没有可以填充的广告"),
	
	/**
	 * 广告位已经关闭
	 */
	ADVERT_SLOT_CLOSED(ExceptionCode.AdxApiCode.ADVERT_SLOT_CLOSED, 200, "广告位已关闭，请联系对接人!"),
	
	
	
	
	
	/**
	 * OCPX监测数据上报失败
	 */
	REPORT_MONITOR_DATA_FAIL(ExceptionCode.OcpxApiCode.REPORT_MONITOR_DATA_FAIL, 200, "监测数据上报失败"),
	
	
	
	
	/**
	 * 必须先删除流量方已关联的应用
	 */
	MUST_DELETE_SSP_APP(ExceptionCode.AdxManageCode.MUST_DELETE_SSP_APP, 200, "必须先删除流量方已关联的应用"),
	
	/**
	 * 必须先删除应用已关联的广告位
	 */
	MUST_DELETE_SSP_SLOT(ExceptionCode.AdxManageCode.MUST_DELETE_SSP_SLOT, 200, "必须先删除应用已关联的广告位"),
	
	/**
	 * 没有找到待审核应用
	 */
	NOT_FOUND_SSP_APP_AUDIT(ExceptionCode.AdxManageCode.NOT_FOUND_SSP_APP_AUDIT, 200, "没有找到待审核应用"),
	
	/**
	 * 没有找到待审核广告位
	 */
	NOT_FOUND_SSP_SLOT_AUDIT(ExceptionCode.AdxManageCode.NOT_FOUND_SSP_SLOT_AUDIT, 200, "没有找到待审核广告位"),
	
	/**
	 * 必须先删除预算方已关联的预算
	 */
	MUST_DELETE_DSP_BUDGET(ExceptionCode.AdxManageCode.MUST_DELETE_DSP_BUDGET, 200, "必须先删除预算方已关联的预算"),
	
	/**
	 * 必须先删除广告位已关联的预算
	 */
	MUST_DELETE_SLOT_BUDGET(ExceptionCode.AdxManageCode.MUST_DELETE_SLOT_BUDGET, 200, "必须先删除广告位已关联的预算"),
	
	
	/**
	 * 不支持此文件类型
	 */
	NO_SUPPORTED_FILE_TYPE(ExceptionCode.AdxManageCode.NO_SUPPORTED_FILE_TYPE, 200, "不支持此文件类型"),
	
	/**
	 * 没有Multipart
	 */
	NO_MULTIPART(ExceptionCode.AdxManageCode.NO_MULTIPART, 200, "没有Multipart"),
	
	/**
	 * 没有匹配到导出数据
	 */
	NOT_FOUND_EXPORT_DATA(ExceptionCode.AdxManageCode.NOT_FOUND_EXPORT_DATA, 200, "没有匹配到导出数据"),

	/********************************讯飞*****************************************/

	/**
	 * 讯飞专用
	 */
	XUNFEI_ADVERT_NOT_FILL(ExceptionCode.AdxApiCode.ADVERT_NOT_FILL, 204, "没有可以填充的广告");
	
	private ExceptionEnum(Integer code, Integer statusCode, String message){
		this.code = code;
		this.statusCode = statusCode;
		this.message = message;
	}

	private Integer code;

	private Integer statusCode;
	
	private String message;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
