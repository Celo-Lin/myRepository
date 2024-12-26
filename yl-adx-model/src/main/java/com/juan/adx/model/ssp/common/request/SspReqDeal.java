package com.juan.adx.model.ssp.common.request;

import lombok.Data;

/**
 * 交易信息
 */
@Data
public class SspReqDeal {

	/**
	 * <pre>
	 * 出价明文，单位：分(竞价模式存在)
	 * 单位：分人民币/千次曝光
	 * 竞价模式必填
	 * </pre>
	 */
	private Integer bidfloor;
	
	/**
	 * <pre>
	 * 计价方式
	 * 	1：ecpm
	 * 	2：cpc
	 * </pre>
	 */
	private Integer chargetype;
}
