package com.juan.adx.model.dsp.yueke.enums;

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
public enum YueKeCoordinateType {
    
	GPS(1, 1, "WGS84全球卫星定位系统坐标系"),
	
	NAD(2, 2, "GCJ02国家测绘局坐标系"),
    
	BAIDU(3, 3, "BD09百度坐标系"),

//	GAODE(4, "高德坐标系"),

//	TENCENT(5, "腾讯坐标系"),
	
//	GOOGLE(6, "谷歌坐标系"),
	
	OTHER(100, 0, "其它"),
	
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
	
	private YueKeCoordinateType(int type, int dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }
	
	public static YueKeCoordinateType get(Integer type){
		if(type == null){
			return OTHER;
		}
		for (YueKeCoordinateType coordinateType : values()) {
			if(coordinateType.getType() == type.intValue()){
				return coordinateType;
			}
		}
		return OTHER;
	}
}
