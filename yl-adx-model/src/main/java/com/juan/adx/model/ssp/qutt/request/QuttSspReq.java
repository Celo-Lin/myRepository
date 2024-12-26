package com.juan.adx.model.ssp.qutt.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18
 * @Description: 趣头条ssp请求外层对象
 * @Version: 1.0
 */
@Data
public class QuttSspReq {

	/**
	 * 是
	 * ⼩于128字节
	 * ADX 提供的 Bid Request 唯⼀标识
	 */
	private String id;

	/**
	 * 否
	 * true表示探测⽹络延迟，不触发竞价逻辑。DSP设置
	 * request_id和processing_time_ms后⽴即返回Bid
	 * Response。DSP必须⽀持此特性。
	 */
	@JSONField(name = "is_ping")
	private Boolean isPing;

	/**
	 否
	 true表示测试请求，竞价成功的⼴告不会被展示和计
	 费。DSP对此类请求的处理完全同普通请求。DSP必
	 须⽀持此特性。
	 */
	@JSONField(name = "is_test")
	private Boolean isTest;

	/**
	 * 每个impression 代表⼀个⼴告位的请求。
	 * 否
	 */
	private List<QuttSspReqImpression > impressions;

	/**
	 * 否
	 * 设备信息。
	 */
	private QuttSspReqDevice device;

	/**
	 * 否
	 * ⽤户信息，仅对部分 DSP 开放。
	 */
	private QuttSspReqUser user;

	/**
	 * 否
	 * 地理位置信息。
	 */
	private QuttSspReqGeo geo;

	/**
	 * 否
	 * App信息。
	 */
	private QuttSspReqApp app;

	/**
	 * DSP请求策略ID。
	 * 否
	 */
	@JSONField(name = "request_strategy")
	private List<Integer> requestStrategy;
}
