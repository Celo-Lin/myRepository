package com.juan.adx.model.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * GPS 坐标类型
 * 1：WGS84(全球卫星定位系统坐标系)
 * 2：GCJ02（国家测绘局坐标系）
 * 3：BD09（百度坐标系）
 * 4：高德坐标系
 * 5：腾讯坐标系
 * 6：谷歌坐标系
 * 100：其它
 * </pre>
 */
public enum CoordinateType {
	
	GPS(1, "WGS84全球卫星定位系统坐标系"),
	
	NAD(2, "GCJ02国家测绘局坐标系(火星坐标)"),
    
	BAIDU(3, "BD09百度坐标系"),

	GAODE(4, "高德坐标系"),

	TENCENT(5, "腾讯坐标系"),
	
	GOOGLE(6, "谷歌坐标系"),
	
	OTHER(100, "其它"),
	
	;
	
	@Getter
	@Setter
	private int type;
	
	@Getter
	@Setter
	private String desc;
	
	private CoordinateType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
	
	public static CoordinateType get(Integer type){
		if(type == null){
			return null;
		}
		for (CoordinateType coordinateType : values()) {
			if(coordinateType.getType() == type.intValue()){
				return coordinateType;
			}
		}
		return null;
	}
}
