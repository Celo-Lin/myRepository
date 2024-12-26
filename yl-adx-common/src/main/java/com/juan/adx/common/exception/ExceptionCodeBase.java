package com.juan.adx.common.exception;

public interface ExceptionCodeBase{
	
	/**
	 * 正确，下面对正确状态码的定义，正确状态码数值<=1000
	 */
	// success code 使用200范围
	int SUCCESS 	= 200;
	// 重定向
	int REDIRECT 	= 300;
	// 路由
	int ROUTER 		= 700;

	/**
	 * 出错，下面对错误状态码的定义，错误状态码数值>=100000
	 * 
	 * 每个error code由 6 位的整型数字，分 3 段有含义的数值表示；具体定义方式后面有举例 
	 * 第1段 由第1位数字表示，其代表error的出处 1:server，2:open api，9:other; 如 1XXXXX，为每个类别定义起始位 
	 * 第2段 由第2～3位数字表示，代表error的业务类别
	 * 第3段 由第4～6位数字表示，自然增长，定义是＋
	 */

	// 第一段定义
	int SERVER_ERROR	= 100000;	//业务接口
	int OPEN_API_ERROR	= 200000;	//对外开放平台接口
	int OTHER_ERROR 	= 900000;	//其它

	// 第二段定义,此段数字单独没有意义,必需与第一段及具体错误码结合使用
	int SERVICE 		= 10000; // 业务代码出错
	int SECURITY 		= 20000; // 安全类出错,试图操作未授权资源
	int INTERNAL 		= 30000; // 访问公司内部其它平台或服务化接口错误
	int THIRD 			= 40000; // 访问第三方组件错误
	int CACHE	 		= 50000; // 缓存类访问错误
	int DATABASE 		= 60000; // 数据库访问错误


	// error code 使用>=100000 && <=999999范围，其中server error code 使用>=100000 && <=199999范围
	// server服务端错务码定义
	int UNCLASSIFIED = SERVER_ERROR + 0; // 100000 表示为服务端未知错误unknow，可视为无定义时的默认值undefine
	
	
	
	
}
