package com.juan.adx.model.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * 客户端网络类型:
 * 0：unkown
 * 1：wifi
 * 2：2G
 * 3：3G
 * 4：4G
 * 5：5G
 * 10：以太网
 * </pre>
 */
public enum NetworkType {

	UNKNOWN(0, "未知"),
	
	WIFI(1, "wifi"),
    
    G2(2, "2G"),
    
    G3(3, "3G"),
    
    G4(4, "4G"),
    
    G5(5, "5G"),
    
    ETHERNET(10, "以太网"),

	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private String desc;
	
	private NetworkType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
	
	public static NetworkType get(Integer type){
		if(type == null){
			return null;
		}
		for (NetworkType connectionType : values()) {
			if(connectionType.getType() == type.intValue()){
				return connectionType;
			}
		}
		return null;
	}

	
}
