package com.juan.adx.model.ssp.common.request;

import lombok.Data;

/**
 * 地理位置信息
 */
@Data
public class SspReqGeo {
	
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
	private Integer coordinateType;

	/**
	 * 纬度，取值范围-90.0 到 90.0， 负值表示南方【注意：要求按照 WGS84 坐标系输出经纬度】
	 */
	private Double latitude;
	
	/**
	 * 经度，取值范围-180.0 到180.0, 负值表示西方【注意：要求按照 WGS84 坐标系输出经纬度】
	 */
	private Double longitude;
	
	/**
	 * 获取坐标信息的时间戳,单位：毫秒
	 */
	private Long timestamp;
	
}
