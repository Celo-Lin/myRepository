package com.juan.adx.model.ssp.qutt.request;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-05-18
 * @Description: Impression
 * @Version: 1.0
 */
@Data
public class QuttSspReqImpression {

	/**
	 * Bid Request范围内， Impression唯⼀标识
	 * ⼩于128字节
	 * 必填字段
	 */
	private String id;

	/**
	 * ADX提供的⼴告位id
	 * 必填字段
	 */
	@JSONField(name = "adslot_id")
	private String adslotId;

	/**
	 * ⼴告位的CPM 底价。单位：分
	 * 必填字段
	 */
	@JSONField(name = "bid_ﬂoor")
	private Integer bidFloor;

	/**
	 * ⼴告位过滤的⾏业ID列表。暂不⽀持
	 */
	@JSONField(name = "blocking_industry_id")
	private List<Integer> blockingIndustryId;

	/**
	 * ⼴告位过滤的⾏业ID列表。暂不⽀持
	 */
	private List<QuttSspReqNative> natives;


	/**
	 * ⼴告位⽀持的素材类型。参考附录
	 */
	@JSONField(name = "multimedia_type_white_list")
	private List<String> multimediaTypeWhiteList;
	/**
	 * ⼴告位过滤的关键字，模糊匹配
	 */
	@JSONField(name = "blocking_keyword")
	private List<String> blockingKeyword;


	/**
	 *是否⽀持deeplink
	 */
	@JSONField(name = "support_deep_link")
	private Boolean supportDeepLink;
	/**
	 *1只⽀持https，2只⽀持http，3两者都⽀持
	 */
	private Integer https;
	/**
	 *出价⽅式，1： CPC， 2： CPM
	 */
	@JSONField(name = "charge_type")
	private Integer chargeType;



}
