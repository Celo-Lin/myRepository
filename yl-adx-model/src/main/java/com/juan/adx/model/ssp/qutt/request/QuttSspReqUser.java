package com.juan.adx.model.ssp.qutt.request;

import lombok.Data;

import java.util.List;


/**
 * 用户信息
 */
@Data
public class QuttSspReqUser {

	/**
	 * <pre>
	 * 用户性别
	 * 	0：其他
	 * 	1：男
	 * 	2：女
	 * </pre>
	 */
	private Integer gender;
	
	/**
	 * <pre>
	 * 用户的年龄： 
	 未知年龄段
	 0
	 AGEGROUP_UNKNOWN
	 1
	 AGEGROUP_18
	 [0,18)
	 2
	 AGEGROUP_22
	 [18,23)
	 3
	 AGEGROUP_39
	 [23,40)
	 4
	 AGEGROUP_40
	 40+
	 * </pre>
	 */
	private Integer age;
	
}
