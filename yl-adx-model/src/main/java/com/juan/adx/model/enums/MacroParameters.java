package com.juan.adx.model.enums;



import com.juan.adx.model.dsp.vivo.enums.VivoMacro;
import com.juan.adx.model.dsp.wifi.enums.WifiMacro;


import com.juan.adx.model.dsp.haoya.enums.HaoYaMacro;
import com.juan.adx.model.dsp.oppo.enums.OppoMacro;
import com.juan.adx.model.dsp.yidian.enums.YiDianMacro;
import com.juan.adx.model.dsp.youdao.enums.YouDaoMacro;
import com.juan.adx.model.dsp.yueke.enums.YueKeMacro;
import com.juan.adx.model.entity.api.MacroParamterMapping;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 上报链接宏参处理器
 */
public enum MacroParameters {
	
	/**
	 * 宏参字典
	 */
	TS_MS("__MF_EVENT_TIME_MS__", "执行上报操作时客户端的时间戳, 单位：毫秒"),
	TS_S("__MF_EVENT_TIME__", "执行上报操作时客户端的时间戳, 单位：秒"),
	
	EVENT_END_TIME_MS("__MF_EVENT_END_TIME_MS__", "事件触发结束的时间戳，单位：毫秒，例如：展示事件，是发送曝光的结束时间。点击事件，是用户点击广告抬起那一刻的时间"),
	EVENT_END_TIME("__MF_EVENT_END_TIME__", "事件触发结束的时间戳，单位：秒，例如：展示事件，是发送曝光的结束时间。点击事件，是用户点击广告抬起那一刻的时间"),
	
	CLICK_ID("__MF_CLICK_ID__", "广点通下载类广告ID，详见广点通广告说明"),
	CLICKAREA("__MF_CLICKAREA__", "点击区域：1-广告素材、2-按钮"),
	DPLINK("__MF_DPLINK__", "是否用deeplink唤醒：1-打开deeplink、2-打开落地页"),
	DPLINK_RESULT("__MF_DPLINK_RESULT__", "deeplink调起结果：0-成功、1-失败"),
	DPLINK_FAIL_REASON("__MF_DPLINK_FAIL_RESULT__", "deeplink调起失败原因：1-被拦截、2-应用未安装、3-调起异常"),
	
	WIDTH("__MF_WIDTH__", "实际广告位的宽度，物理像素，单位：像素"),
	HEIGHT("__MF_HEIGHT__", "实际广告位的高度，物理像素，单位：像素"),
	REQ_WIDTH("__MF_REQ_WIDTH__", "广告请求时的广告位宽，物理像素，单位：像素"),
	REQ_HEIGHT("__MF_REQ_HEIGHT__", "广告请求时的广告位高，物理像素，单位：像素"),
	
	DOWN_X("__MF_ABS_DOWN_X__", "用户手指按下时的横坐标(绝对坐标：以屏幕左上角坐标为原点)"),
	DOWN_Y("__MF_ABS_DOWN_Y__", "用户手指按下时的纵坐标(绝对坐标：以屏幕左上角坐标为原点)"),
	UP_X("__MF_ABS_UP_X__", "用户手指抬起时的横坐标(绝对坐标：以屏幕左上角坐标为原点)"),
	UP_Y("__MF_ABS_UP_Y__", "用户手指抬起时的纵坐标(绝对坐标：以屏幕左上角坐标为原点)"),
	
	POS_DOWN_X("__MF_POS_DOWN_X__", "用户手指按下时的横坐标(相对坐标：以广告位左上角坐标为原点)"),
	POS_DOWN_Y("__MF_POS_DOWN_Y__", "用户手指按下时的纵坐标(相对坐标：以广告位左上角坐标为原点)"),
	POS_UP_X("__MF_POS_UP_X__", "用户手指抬起时的横坐标(相对坐标：以广告位左上角坐标为原点)"),
	POS_UP_Y("__MF_POS_UP_Y__", "用户手指抬起时的纵坐标(相对坐标：以广告位左上角坐标为原点)"),
	
	DP_WIDTH("__MF_DP_WIDTH__", "实际广告位的宽度，取逻辑像素值，Android端单位为(dp)，ios端单位为(pt)"),
	DP_HEIGHT("__MF_DP_HEIGHT__", "实际广告位的高度，取逻辑像素值，Android端单位为(dp)，ios端单位为(pt)"),
	DP_POS_DOWN_X("__MF_POS_DP_DOWN_X__", "用户手指按下时的横坐标(相对坐标)，取逻辑像素值，Android端单位为(dp)，ios端单位为(pt)"),
	DP_POS_DOWN_Y("__MF_POS_DP_DOWN_Y__", "用户手指按下时的纵坐标(相对坐标)，取逻辑像素值，Android端单位为(dp)，ios端单位为(pt)"),
	DP_POS_UP_X("__MF_POS_DP_UP_X__", "用户手指抬起时的横坐标(相对坐标)，取逻辑像素值，Android端单位为(dp)，ios端单位为(pt)"),
	DP_POS_UP_Y("__MF_POS_DP_UP_Y__", "用户手指抬起时的纵坐标(相对坐标)，取逻辑像素值，Android端单位为(dp)，ios端单位为(pt)"),
	
	AIT("__MF_AIT__", "广告点击交互方式(Ad Interaction Type)：0-正常触屏点击、1-引导滑动点击、2-摇动点击(摇一摇)、3-自定义手势、4-正常触屏点击 或 滑动点击、5-扭动点击(扭一扭)、6-擦除"),
	X_MAX_ACC("__MF_X_MAX_ACC__", "SLD=2 时必填，用户摇动点击时 x 轴加速度峰值。Android 端乘以 100 取整， iOS 端乘以 980 取整"),
	Y_MAX_ACC("__MF_Y_MAX_ACC__", "SLD=2 时必填，用户摇动点击时 y 轴加速度峰值。Android 端乘以 100 取整，iOS 端乘以980 取整"),
	Z_MAX_ACC("__MF_Z_MAX_ACC__", "SLD=2 时必填，用户摇动点击时 z 轴加速度峰值。Android 端乘以 100 取整，iOS 端乘以980 取整"),
	TURN_X("__MF_TURN_X__", "SLD=5 时必填，对比扭动前初始位置，扭动触发点击时的 x 轴扭动角度，为-180 到 180 的整数"),
	TURN_Y("__MF_TURN_Y__", "SLD=5 时必填，对比扭动前初始位置，扭动触发点击时的 y 轴扭动角度，为-180 到 180 的整数"),
	TURN_Z("__MF_TURN_Z__", "SLD=5 时必填，对比扭动前初始位置，扭动触发点击时的 z 轴扭动角度，为-180 到 180 的整数"),
	TURN_TIME("__MF_TURN_TIME__", "SLD=5 时必填，扭动触发点击时扭动的总时间（单位毫秒，保留整数）。即最后一次监听到 3 个方向扭动角度均小于±5 度，到点击触发的时间"),
	
	
	VIDEO_TIME("__MF_VIDEO_TIME__", "视频总时长，单位：秒"),
	VIDEO_PLAY_BEGIN_TIME("__MF_PLAY_BEGIN_TIME__", "视频播放开始时间，单位：秒。如果视频从头开始播放，则为 0"),
	VIDEO_PLAY_END_TIME("__MF_PLAY_END_TIME__", "视频播放结束时间，单位：秒。如果视频播放到结尾，则等于视频总时长"),
	VIDEL_PLAY_FIRST_FRAME("__MF_PLAY_FIRST_FRAME__", "视频是否从第⼀帧开始播放：1-是、0-否"),
	VIDEL_PLAY_LAST_FRAME("__MF_PLAY_LAST_FRAME__", "视频是否播放到最后一帧：1-是、0-否"),
	VIDEO_PLAY_PROGRESS_MS("__MF_PLAY_PROGRESS_MS__", "视频已播放的时长, 单位：毫秒"),
	VIDEO_PLAY_PROGRESS("__MF_PLAY_PROGRESS__", "视频已播放的时长, 单位：秒"),
	VIDEO_PLAY_SCENE("__MF_PLAY_SCENE__", "视频播放场景："
			+ "1-在广告曝光区域播放"
			+ "2-全屏竖屏,只展示视频"
			+ "3-全屏竖屏,屏幕上方展示视频,下方展示广告推广目标网页(仅适用于交互类型是打开网页的广告)"
			+ "4-全屏横屏,只展示视频"
			+ "0-开发者自定义"),
	VIDEO_PLAY_TYPE("__MF_PLAY_TYPE__", "视频播放类型：1-第一次开始播放、2-暂停后继续播放、3-重新开始播放"),
	VIDEO_PLAY_BEHAVIOR("__MF_PLAY_BEHAVIOR__", "视频播放行为：1-自动播放、2-点击播放"),
	VIDEO_PLAY_STATUS("__MF_PLAY_STATUS__", "视频播放状态：0-正常播放、1-视频加载中、2-播放错误"),
	
	PRICE("__MF_PRICE__","价格宏,曝光上报和竞胜上报时，进行价格宏替换(需要加密)，加密算法查看5.3"),
	LOSS_REASON("__MF_LOSS_REASON__","竞败上报原因替换"),
	
	;
	
	@Setter
	@Getter
	private String macro;
	
	@Setter
	@Getter
	private String desc;
	
	private MacroParameters(String macro, String desc) {
        this.macro = macro;
        this.desc = desc;
    }

	public static List<String> macroParametersReplace(Dsp dsp, List<String> urls){
		if(urls==null || urls.isEmpty()){
			return null;
		}
		List<MacroParamterMapping> macroParamterMappings = getMacroParameterMappings(dsp);
		List<String> resultList = new ArrayList<String>();
		urls.stream().forEach(url -> {
			for (MacroParamterMapping macroParamterMapping : macroParamterMappings) {
	            if (StringUtils.containsIgnoreCase(url, macroParamterMapping.getPartnerMacro())) {
	                url = StringUtils.replaceOnceIgnoreCase(url, macroParamterMapping.getPartnerMacro(), macroParamterMapping.getMacro());
	            }
	        }
	        resultList.add(url);
		});
		return resultList;
	}
	
	private static Map<Dsp, List<MacroParamterMapping>> macroParameterMappingsCache = new ConcurrentHashMap<>();

	private static List<MacroParamterMapping> getMacroParameterMappings(Dsp dsp) {
	    return macroParameterMappingsCache.computeIfAbsent(dsp, d -> {
	        switch (d) {
	            case YUE_KE:
	                return YueKeMacro.getMacroParamterMappings();
	            case HAO_YA:
	            	return HaoYaMacro.getMacroParamterMappings();
	            case OPPO:
	            	return OppoMacro.getMacroParamterMappings();
				case YOU_DAO:
					return YouDaoMacro.getMacroParamterMappings();
				case YI_DIAN:
					return YiDianMacro.getMacroParamterMappings();
                case VIVO:
                    return VivoMacro.getMacroParamterMappings();
                case WIFI:
                    return WifiMacro.getMacroParamterMappings();
	            default:
	                return Collections.emptyList();
	        }
	    });
	}
	
}
