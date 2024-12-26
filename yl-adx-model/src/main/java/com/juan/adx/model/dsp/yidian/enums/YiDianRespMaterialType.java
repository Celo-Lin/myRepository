package com.juan.adx.model.dsp.yidian.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 *  参与竞价的广告位模板类型
 * </pre>
 */
public enum YiDianRespMaterialType {

	UNKNOWN(0, 0, "未知"),

//	BANNER(1, 3, "横幅"),

//	INTERSTITIAL(2, 2, "插屏"),

//    REWARDED_VIDEO(5, 4, "激励视频"),

//    NATIVE(6, 0, "Native原生视频"),

    STREAM_BIG_IMAGE(2, 1, "大图样式"),
    STREAM_SMALL_IMAGE(2, 2, "小图样式"),
    STREAM_GROUP_IMAGE(2, 3, "组图样式"),
    STREAM_VERTICAL_VIDEO(5, 4, "竖版视频"),
    STREAM_HORIZONTAL_VIDEO(5, 5, "横版视频"),
	STREAM_STATIC_SPLASH(2, 6, "静态开屏"),
	STREAM_ANIMATED_SPLASH(2, 7, "动态开屏"),
	STREAM_VIDEO_SPLASH(5, 8, "视频开屏"),
	STREAM_VERTICAL_IMAGE(2, 9, "竖版图片"),

	;

	@Getter
	@Setter
	private int type;

	@Getter
	@Setter
	private Integer dspType;

	@Getter
	@Setter
	private String desc;

	private YiDianRespMaterialType(int type, Integer dspType, String desc) {
        this.type = type;
        this.dspType = dspType;
        this.desc = desc;
    }
	
	public static YiDianRespMaterialType get(Integer dspType){
		if(dspType == null){
			return UNKNOWN;
		}
		for (YiDianRespMaterialType advertType : values()) {
			if(advertType.getDspType().intValue() == dspType.intValue()){
				return advertType;
			}
		}
		return UNKNOWN;
	}

}
