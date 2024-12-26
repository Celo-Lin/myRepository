package com.juan.adx.model.ssp.common.request;

import lombok.Data;

/**
 * 广告位信息
 */
@Data
public class SspReqSlot {
	
	/**
	 * 此参数仅用于DSP对接我方时使用
	 * DSP的广告位ID
	 */
	private String slotId;
	
	/**
	 * 广告位ID，在广告联盟创建广告位时分配的adSlotId
	 * 必填字段
	 */
	private Integer adSlotId;
	
	
	/**
	 * <pre>
	 *  广告类型：
	 * 	1：横幅广告(banner)
	 * 	2：插屏广告(interstitial)
	 * 	3：开屏广告(splash)
	 * 	4：信息流广告(flow)
	 * 	5：激励视频广告(rewardvod)
	 * 	6：Native原生广告(native)
	 * </pre>
	 */
	private Integer type;
	
	/**
	 * 广告位宽度
	 * 必填字段
	 */
	private Integer width;
	
	/**
	 * 广告位高度
	 * 必填字段
	 */
	private Integer height;
	
    /**
     * <pre>
     * 期望返回的广告物料类型：
     * 	0：未知
     *  1：纯文字广告
     *  2：纯图片广告
     *  3：图文广告
     *  4：HTML广告
     *  5：视频广告
     *  6：音频广告
     * </pre>
     */
    private Integer materialType;
	
}
