package com.juan.adx.model.constants;

import java.util.Arrays;
import java.util.List;

public interface ManageCommonConstants {

	String FAIL = "FAIL";
	
	String WATING = "WATING";

	String SUCCESS = "SUCCESS";
	
	String CALLER_WX_NOTIFY = "微信支付回调";
	
	String CALLER_CREATE_ORDER = "创建订单";
	
	int ID_STEP_SIZE = 100;
	
	public static List<String> WITHOUT_PERMISSION = Arrays.asList(
			"/manage/permission/auth/loginout",		//登出接口
			"/manage/permission/auth/permissions",	//查询当前登录用户权限树接口
			"/manage/adx/common/image/upload",		//通用上传图片接口
			"/manage/adx/index/trend",				//首页趋势数据接口
			"/manage/adx/index/statistics",			//首页收益统计数据接口
			"/manage/adx/index/update_passwd",		//首页修改密码接口
			"/manage/adx/dict/appstore",			//应用商店字典数据接口
			"/manage/adx/dict/industry"				//行业字典数据接口
			);		
	
    
    /**
     * 授权类型
     */
    public interface GrantType {
    	
        String AUTHORIZATION_CODE = "authorization_code";

    }
    
    public interface Password{
    	
    	int rounds = 13;
    }
	  
    /**
     * 签名类型.
     */
    interface SignType {
      /**
       * The constant HMAC_SHA256.
       */
      String HMAC_SHA256 = "HMAC-SHA256";
      /**
       * The constant MD5.
       */
      String MD5 = "MD5";
      /**
       * The constant ALL_SIGN_TYPES.
       */
     List<String> ALL_SIGN_TYPES = Arrays.asList(HMAC_SHA256, MD5);
    }
    
}
