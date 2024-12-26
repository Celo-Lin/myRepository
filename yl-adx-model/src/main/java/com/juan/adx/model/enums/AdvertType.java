package com.juan.adx.model.enums;

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
 *  7：贴片广告
 * </pre>
 */
public enum AdvertType {

	BANNER(1, "横幅"),
	
	INTERSTITIAL(2, "插屏"),

	SPLASH(3, "开屏"),
	
    INFORMATION_STREAM(4, "信息流"),
    
    REWARDED_VIDEO(5, "激励视频"),
    
    NATIVE(6, "Native原生视频"),
    
    PATCH(7, "贴片广告"),
	
	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private String desc;
	
	private AdvertType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
	
	public static AdvertType get(Integer type){
		if(type == null){
			return null;
		}
		for (AdvertType advertType : values()) {
			if(advertType.getType() == type.intValue()){
				return advertType;
			}
		}
		return null;
	}

}
