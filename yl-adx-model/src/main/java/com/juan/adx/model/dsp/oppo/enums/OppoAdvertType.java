package com.juan.adx.model.dsp.oppo.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 *  广告类型：
 * 	1：横幅广告(banner)
 * 	2：插屏广告(interstitial)
 * 	3：开屏广告(splash)
 * 	4：信息流广告(flow)
 * 	5：激励视频广告(rewardvod)
 * 	6：Native原生广告视频(native)
 * </pre>
 */
public enum OppoAdvertType {

	BANNER(1, 1, "横幅"),
	
	INTERSTITIAL(2, 2, "插屏"),

	SPLASH(3, 4, "开屏"),
	
    INFORMATION_STREAM(4, 8, "信息流"),
    
    REWARDED_VIDEO(5, 64, "激励视频"),
    
    NATIVE(6, 8, "Native原生视频")
	
	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private int dspType;
	
	@Getter
	@Setter
	private String desc;
	
	private OppoAdvertType(int type, int dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }
	
	public static OppoAdvertType get(Integer type){
		if(type == null){
			return INFORMATION_STREAM;
		}
		for (OppoAdvertType advertType : values()) {
			if(advertType.getType() == type.intValue()){
				return advertType;
			}
		}
		return INFORMATION_STREAM;
	}

}
