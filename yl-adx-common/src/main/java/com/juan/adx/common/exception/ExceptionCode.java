package com.juan.adx.common.exception;

public interface ExceptionCode extends ExceptionCodeBase {

	interface CommonCode{
		
		/** DB层通用错误码 */
		int DATABASE_ERROR = SERVER_ERROR + DATABASE + 0;
		
		/** 非法参数*/
		int INVALID_PARAM = SERVER_ERROR + SERVICE +  1;
		
		int SIGN_INVALID = SERVER_ERROR + SERVICE +  2;
		
		int UNAUTHORIZED = SERVER_ERROR + SECURITY + 1;
		
		int FORBIDDEN = SERVER_ERROR + INTERNAL + 1;
		
		
		
		
	}
	
	interface PermissionCode{

		int BASE_CODE = 100;

		/**
		 * 账号或者密码错误
		 */
		int ACCOUNT_PASSWORE_ERROR = SERVER_ERROR + SERVICE + BASE_CODE + 1;

		int CHANGE_PASSWORD_ERROR = SERVER_ERROR + SERVICE + BASE_CODE + 2;

	}
	
	interface AdxApiCode{
		
		int BASE_CODE = 900;
		
		/**
		 * 没有配置广告位预算
		 */
		int NOT_CONFIG_BUDGET = OPEN_API_ERROR + SERVICE + BASE_CODE + 19;
		
		/**
		 * 无效的广告位ID
		 */
		int INVALID_SLOT_ID = OPEN_API_ERROR + SERVICE + BASE_CODE + 20;
		
		/**
		 * 广告位已超过单日最大请求限制
		 */
		int DSP_REQUEST_EXCEED_MAX_LIMIT = OPEN_API_ERROR + SERVICE + BASE_CODE + 21;
		
		/**
		 * 设备已超过单日最大请求限制
		 */
		int DSP_DEVICE_REQUEST_EXCEED_MAX_LIMIT = OPEN_API_ERROR + SERVICE + BASE_CODE + 22;
		
		/**
		 * 该广告位没有配置映射（没有配置DSP映射）
		 */
		int NOT_CONFIG_DSP_MAPPING = OPEN_API_ERROR + SERVICE + BASE_CODE + 23;
		
		/**
		 * 没有填充广告
		 */
		int ADVERT_NOT_FILL = OPEN_API_ERROR + SERVICE + BASE_CODE + 24;
		
		/**
		 * 广告已经关闭
		 */
		int ADVERT_SLOT_CLOSED = OPEN_API_ERROR + SERVICE + BASE_CODE + 25;
		
	}
	
	interface AdxManageCode{
		
		int BASE_CODE = 300;
		
		/**
		 * 必须先删除流量方已关联的应用
		 */
		int MUST_DELETE_SSP_APP = SERVER_ERROR + SERVICE + BASE_CODE + 1;
		
		/**
		 * 必须先删除应用已关联的广告位
		 */
		int MUST_DELETE_SSP_SLOT = SERVER_ERROR + SERVICE + BASE_CODE + 2;
		
		/**
		 * 没有找到待审核应用
		 */
		int NOT_FOUND_SSP_APP_AUDIT = SERVER_ERROR + SERVICE + BASE_CODE + 3;
		
		/**
		 * 必须先删除预算方已关联的预算
		 */
		int MUST_DELETE_DSP_BUDGET = SERVER_ERROR + SERVICE + BASE_CODE + 4;
		
		/**
		 * 必须先删除广告位已关联的预算
		 */
		int MUST_DELETE_SLOT_BUDGET = SERVER_ERROR + SERVICE + BASE_CODE + 5;
		
		/**
		 * 不支持此文件类型
		 */
		int NO_SUPPORTED_FILE_TYPE = SERVER_ERROR + SERVICE + BASE_CODE + 6;
		
		/**
		 * 没有Multipart
		 */
		int NO_MULTIPART = SERVER_ERROR + SERVICE + BASE_CODE + 6;
		
		/**
		 * 没有匹配到导出数据
		 */
		int NOT_FOUND_EXPORT_DATA = SERVER_ERROR + SERVICE + BASE_CODE + 7;
		
		/**
		 * 没有找到待审核广告位
		 */
		int NOT_FOUND_SSP_SLOT_AUDIT = SERVER_ERROR + SERVICE + BASE_CODE + 8;
		
	}
	
	interface OcpxApiCode{
		
		int BASE_CODE = 400;
		
		/**
		 * OCPX监测数据上报失败
		 */
		int REPORT_MONITOR_DATA_FAIL = SERVER_ERROR + SERVICE + BASE_CODE + 1;
		
	}

	
}
