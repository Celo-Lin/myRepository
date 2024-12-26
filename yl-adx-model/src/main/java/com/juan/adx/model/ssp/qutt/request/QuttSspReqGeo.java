package com.juan.adx.model.ssp.qutt.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 地理位置信息
 */
@Data
public class QuttSspReqGeo {
	
	/**
	 纬度。采⽤WGS-84坐标系统标
	 准
	 否
	 */
	private Double lat;

	/**
	 经度。采⽤WGS-84坐标系统标
	 准
	 否
	 */
	private Double lon;
	
	/**
	 * 经纬度获取时的时间戳
	 * （unixtime，单位：秒）
	 * 否
	 */
	@JSONField(name = "loc_time")
	private Long locTime;
	
	/**
	 * 否
	 * GPS坐标系类型。 0：GCJ-02;
	 * 1:WGS-84; 2：bd09ll
	 */
	@JSONField(name = "lbs_type")
	private Integer lbsType;
	
}
