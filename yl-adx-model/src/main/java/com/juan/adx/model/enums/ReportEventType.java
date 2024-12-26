package com.juan.adx.model.enums;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * 上报事件类型
 */
public enum ReportEventType {

	REQUEST("request", "请求事件"),
	
    FILL("fill", "填充事件"),
    
    DISPLAY("display", "展示事件"),
    
    CLICK("click", "点击事件"),
    
    DOWNLOAD("download", "下载事件"),
	
	INSTALL("install", "安装事件"),
	
	DEEPLINK("deeplink", "dp事件"),
	
	;
	
	@Getter
	@Setter
	private String type;
	
	@Getter
	@Setter
	private String desc;
	
	private ReportEventType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
	
	public static ReportEventType get(String type){
		if(type == null || type.isEmpty()){
			return null;
		}
		for (ReportEventType eventType : values()) {
			if(Objects.equals(eventType.getType(), type)){
				return eventType;
			}
		}
		return null;
	}
	
}
