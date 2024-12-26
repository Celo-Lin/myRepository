package com.juan.adx.model.ssp.qutt.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 *  广告类型：
 ⼤图；需要返回title， desc(可选)，imgs（⼀张⼤ 图）:  1
 图⽂；需要返回 title,desc,imgs（⼀张缩略 图）: 2
 组图（⼀般是3图）；需要 返回title,desc(可选),imgs （多张缩略图）: 3
 互动⼴告；需要返回 title,desc,imgs(⼀张⼤图， ⼀张icon): 4
 开屏；需要返回⼤图或者视 频: 5
 横幅；图⽚ :6
 视频；title,desc,视频 : 7
 激励视频；title,desc,图标, ⼤图(定帧图), 视频,[可选： 评论⼈数，评分]: 8

 期望返回的广告物料类型：
 0：未知
 1：纯文字广告
 2：纯图片广告
 3：图文广告
 4：HTML 广告
 5：视频广告
 6：音频广告
 * </pre>
 */
public enum QuttAdvertMaterialType {

	UNKNOWN(0, 0, "未定义"),

	BIG_IMAGE(2, 1, "横幅"),

	INTERSTITIAL(3, 2, "插屏"),

	INFORMATION_STREAM(4, 1, "信息流"),

	SPLASH(3, 5, "开屏"),

	BANNER(3, 6, "横幅；图⽚"),

    VIDEO(5, 7, "视频；title,desc,视频"),

    REWARDED_VIDEO(5, 8, "激励视频"),

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

	private QuttAdvertMaterialType(int type, int sspType, String desc) {
        this.type = type;
        this.sspType = sspType;
        this.desc = desc;
    }
	
	public static QuttAdvertMaterialType getBySspType(Integer sspType){
		if(sspType == null){
			return UNKNOWN;
		}
		for (QuttAdvertMaterialType advertType : values()) {
			if(advertType.getSspType() == sspType){
				return advertType;
			}
		}
		return UNKNOWN;
	}

	public static QuttAdvertMaterialType getByType(Integer type){
		if(type == null){
			return UNKNOWN;
		}
		for (QuttAdvertMaterialType advertType : values()) {
			if(advertType.getType() == type){
				return advertType;
			}
		}
		return UNKNOWN;
	}

}
