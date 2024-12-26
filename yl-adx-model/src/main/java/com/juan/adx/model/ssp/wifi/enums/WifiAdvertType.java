package com.juan.adx.model.ssp.wifi.enums;

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
public enum WifiAdvertType {
	
	UNKNOWN(0, 0, "未定义"),

	BANNER(1, 4, "横幅"),
	
	INTERSTITIAL(2, 3, "插屏"),

	SPLASH(3, 2, "开屏"),
	
    INFORMATION_STREAM(4, 1, "信息流"),
    
    REWARDED_VIDEO(5, 5, "激励视频"),
    
	
	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private int sspType;
	
	@Getter
	@Setter
	private String desc;
	
	private WifiAdvertType(int type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }
	
	public static WifiAdvertType getBySspType(Integer sspType){
		if(sspType == null){
			return UNKNOWN;
		}
		for (WifiAdvertType advertType : values()) {
			if(advertType.getSspType() == sspType.intValue()){
				return advertType;
			}
		}
		return UNKNOWN;
	}

}
