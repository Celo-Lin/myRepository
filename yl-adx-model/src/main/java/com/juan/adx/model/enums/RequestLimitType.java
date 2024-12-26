package com.juan.adx.model.enums;

import lombok.Getter;
import lombok.Setter;

public enum RequestLimitType {
	
	REQUEST(1, "请求数限制"),
	
    DISPLAY(2, "展示数限制"),
    
    CLICK(3, "点击数限制"),
    
    DEEPLINK_DOWNLOAD(4, "dp + 下载数限制"),
    
    INSTALLED(5, "安装数限制")
	
	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private String desc;
	
	private RequestLimitType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
	
	public static RequestLimitType get(Integer type){
		if(type == null){
			return null;
		}
		for (RequestLimitType requestLimitType : values()) {
			if(requestLimitType.getType() == type.intValue()){
				return requestLimitType;
			}
		}
		return null;
	}
}
