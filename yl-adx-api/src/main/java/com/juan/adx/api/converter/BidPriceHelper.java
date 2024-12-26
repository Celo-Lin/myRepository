package com.juan.adx.api.converter;

import com.juan.adx.model.entity.api.BidRecord;
import com.juan.adx.model.enums.CooperationMode;

/**
 * <pre>
 * RTB（手动）:
 * 	请求DSP价格：拿 上游底价 报给DSP
 * 	返回SSP价格计算公式：
 *		CASE-1: DSP返回的价格 > 媒体底价
 *			*无需返回价格给SSP
 *	
 *		CASE-2: DSP返回的价格 <= 媒体底价
 *			*不返回广告给SSP
 *	
 * RTB（自动）：
 * 	请求DSP价格计算公式：(SSP上报底价 * 底价上浮比率) + SSP上报底价
 * 	返回SSP价格计算公式：
 *		CASE-1: DSP返回的价格 > 请求DSP时价格
 *			*计算响应给SSP的价格公式：(ssp上报底价 * 溢价率) + ssp上报底价
 *
 *		CASE-2：DSP返回的价格 <= 请求DSP时价格
 *			*计算响应给SSP的价格公式：DSP返回价格 - (DSP返回价格 * 底价上浮比率)
 *	</pre>
 */
public class BidPriceHelper {

	/**
	 * <pre>
	 * 计算请求DSP时的出价
	 * 	RTB自动模式，请求DSP价格计算公式：(SSP上报底价 * 底价上浮比率) + SSP上报底价
	 * 	RTB手动模式，请求DSP价格：拿 上游底价 报给DSP
	 * </pre>
	 * @param bidRecord			出价记录
	 * @param sspRequestParam	SSP价格入参
	 * @return	请求DSP时的价格，单位：分
	 */
	public static Integer calcRequestToDspPriceForRtbAuto(BidRecord bidRecord, Integer sspBidPrice, CooperationMode cooperationMode) {
		int dspFloor = 0;
		if(sspBidPrice == null || sspBidPrice.intValue() <= 0) {
			sspBidPrice = 0;
		}
		switch (cooperationMode) {
			case RTB_AUTO:
				double doublePercentage = bidRecord.getDspFloatingRateSnapshot() / 100.0;
				double dspFloating = sspBidPrice * doublePercentage;
				int dspFloatingInt = (int) dspFloating;
				dspFloor = dspFloatingInt + sspBidPrice;
				bidRecord.setSspBidPrice(sspBidPrice);
				bidRecord.setDspBidPrice(dspFloor);
				break;
			case RTB_MANUAL:
				dspFloor = bidRecord.getDspFloorPriceSnapshot();
				bidRecord.setSspBidPrice(sspBidPrice);
				bidRecord.setDspBidPrice(bidRecord.getDspFloorPriceSnapshot());
				break;
			default:
				break;
		}
		return dspFloor;
	}
	
	
	/**
	 * <pre>
	 * 计算返回给SSP的报价
	 * RTB自动模式，返回SSP价格计算公式：
	 *	CASE-1: DSP返回的价格 - (DSP返回的价格  *溢价率) >= SSP请求
	 *		*计算响应给SSP的价格公式：DSP返回的价格 - (DSP返回的价格  *溢价率)
	 *	CASE-其它: 
	 *		*则丢弃此广告
	 *
	 * RTB手动模式：
	 *	CASE-1: DSP返回的价格 > 媒体底价
	 *		*无需返回价格给SSP
	 *	CASE-2: DSP返回的价格 <= 媒体底价
	 *		*不返回广告给SSP
	 *	CASE-3: DSP返回的价格 为 NULL 或者 DSP返回的价格 <= 0
	 *		*则丢弃此广告
	 * </pre>
	 * @param bidRecord			出价记录
	 * @param dspReturnPrice	DSP返回的价格
	 * @return	返回给SSP的价格
	 */
	public static Integer calcReturnToSspPriceForRtbAuto(BidRecord bidRecord, Integer dspReturnPrice, CooperationMode cooperationMode) {
		Integer sspReturnPrice = 0;
		if(dspReturnPrice == null || dspReturnPrice.intValue() <= 0) {
			bidRecord.setDspReturnPrice(0);
			bidRecord.setSspReturnPrice(0);
			return sspReturnPrice;
		}
		switch (cooperationMode) {
			case RTB_AUTO:
				double doublePercentage = bidRecord.getSspPremiumRateSnapshot() / 100.0;
				Double dspPremium = dspReturnPrice - (dspReturnPrice * doublePercentage);
				if(dspPremium.intValue() >= bidRecord.getSspBidPrice().intValue()) {
					sspReturnPrice = dspPremium.intValue();
				}
				bidRecord.setDspReturnPrice(dspReturnPrice);
				bidRecord.setSspReturnPrice(sspReturnPrice);
				break;
			case RTB_MANUAL:
				if(dspReturnPrice.intValue() > bidRecord.getSspFloorPriceSnapshot().intValue()) {
					sspReturnPrice = 0;
					bidRecord.setDspReturnPrice(dspReturnPrice);
					bidRecord.setSspReturnPrice(sspReturnPrice);
				}
				break;
			default:
				break;
		}
		return sspReturnPrice;
	}
	
	
	/**
	 * @deprecated 初始版本，弃用
	 * 
	 * <pre>
	 * 计算返回给SSP的报价
	 * RTB自动模式，返回SSP价格计算公式：
	 *	CASE-1: DSP返回的价格 > 请求DSP时价格
	 *		*计算响应给SSP的价格公式：(ssp上报底价 * 溢价率) + ssp上报底价
	 *	CASE-2：DSP返回的价格 <= 请求DSP时价格
	 *		*计算响应给SSP的价格公式：DSP返回价格 - (DSP返回价格 * 底价上浮比率)
	 *	CASE-3: DSP返回的价格 为 NULL 或者 DSP返回的价格 <= 0
	 *		*则丢弃此广告
	 *
	 *	FIXME CASE-4：SSP不设底价计算公式 
	 *
	 * RTB手动模式：
	 *	CASE-1: DSP返回的价格 > 媒体底价
	 *		*无需返回价格给SSP
	 *	CASE-2: DSP返回的价格 <= 媒体底价
	 *		*不返回广告给SSP
	 *	CASE-3: DSP返回的价格 为 NULL 或者 DSP返回的价格 <= 0
	 *		*则丢弃此广告
	 * </pre>
	 * @param bidRecord			出价记录
	 * @param dspReturnPrice	DSP返回的价格
	 * @return	返回给SSP的价格
	 */
	public static Integer calcReturnToSspPriceForRtbAuto_InitVersion(BidRecord bidRecord, Integer dspReturnPrice, CooperationMode cooperationMode) {
		Integer sspReturnPrice = 0;
		if(dspReturnPrice == null || dspReturnPrice.intValue() <= 0) {
			bidRecord.setDspReturnPrice(0);
			bidRecord.setSspReturnPrice(0);
			return sspReturnPrice;
		}
		switch (cooperationMode) {
			case RTB_AUTO:
				if(dspReturnPrice.intValue() > bidRecord.getDspBidPrice()) {
					double doublePercentage = bidRecord.getSspPremiumRateSnapshot() / 100.0;
					double dspPremium = bidRecord.getSspBidPrice() * doublePercentage;
					int dspPremiumInt = (int) dspPremium;
					sspReturnPrice = dspPremiumInt + bidRecord.getSspBidPrice();
				}else {
					double doublePercentage = bidRecord.getSspPremiumRateSnapshot() / 100.0;
					double dspPremium = dspReturnPrice * doublePercentage;
					int dspPremiumInt = (int) dspPremium;
					sspReturnPrice = dspReturnPrice - dspPremiumInt;
				}
				bidRecord.setDspReturnPrice(dspReturnPrice);
				bidRecord.setSspReturnPrice(sspReturnPrice);
				break;
			case RTB_MANUAL:
				if(dspReturnPrice.intValue() > bidRecord.getSspFloorPriceSnapshot().intValue()) {
					sspReturnPrice = 0;
					bidRecord.setDspReturnPrice(dspReturnPrice);
					bidRecord.setSspReturnPrice(sspReturnPrice);
				}
				break;
			default:
				break;
		}
		return sspReturnPrice;
	}
	
	
	
}
