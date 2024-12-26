package com.juan.adx.manage.action.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.adx.DspBudgetService;
import com.juan.adx.manage.service.adx.SlotBudgetService;
import com.juan.adx.manage.service.adx.SspAdvertSlotService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.dto.manage.DspBudgetDto;
import com.juan.adx.model.dto.manage.SlotBudgetDto;
import com.juan.adx.model.dto.manage.SspAdvertSlotDto;
import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.enums.RequestLimitType;

@RestController
@RequestMapping("/adx/slotbuget")
public class SlotBudgetAction {
	
	@Resource
	private SlotBudgetService slotBudgetService;
	
	@Resource
	private DspBudgetService dspBudgetService;
	
	@Resource
	private SspAdvertSlotService sspAdvertSlotService;

	
	/**
	 * 根据广告位ID查询广告位预算列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(Integer slotId) {
		ParamAssert.isTrue(slotId == null || slotId <= 0, "广告位ID不能为空");
		List<SlotBudgetDto> data = this.slotBudgetService.listSlotBudgets(slotId);
		return new ManageResponse(data);
	}
	
	/**
	 * 查询广告位预算详情
	 */
	@RequestMapping("/detail")
	public ManageResponse detail(Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "广告预算ID不能为空");
		SlotBudgetDto slotBudegetDto = this.slotBudgetService.getSlotBudget(id);
		return new ManageResponse(slotBudegetDto);
	}
	
	/**
	 * 新增广告位预算
	 */
	@RequestMapping("/add")
	public ManageResponse add(@Validated SlotBudgetDto slotBudget) {
		ParamAssert.isTrue(slotBudget.getDspPartnerId() == null || slotBudget.getDspPartnerId() <= 0, "预算方ID不能为空");
		ParamAssert.isTrue(slotBudget.getBudgetId() == null || slotBudget.getBudgetId() <= 0, "预算ID不能为空");
		ParamAssert.isTrue(slotBudget.getSlotId() == null || slotBudget.getSlotId() <= 0, "广告位ID不能为空");
		//ParamAssert.isBlank(slotBudget.getDspAppId(), "预算方应用ID不能为空");
		//ParamAssert.isBlank(slotBudget.getDspSlotId(), "预算方广告位ID不能为空");
		ParamAssert.isTrue(slotBudget.getLimitType() == null || slotBudget.getLimitType() <= 0, "量级限制类型不能为空");
		ParamAssert.isTrue(slotBudget.getMaxRequests() == null || slotBudget.getMaxRequests() < -1, "每日最大请求数不能为空");
		//ParamAssert.isBlank(slotBudget.getPackageName(), "定向包名不能为空");
		RequestLimitType requestLimitType = RequestLimitType.get(slotBudget.getLimitType());
		ParamAssert.isTrue(requestLimitType == null, "量级限制类型枚举值无效");
		
		SspAdvertSlotDto advertSlotDto = this.sspAdvertSlotService.getSspAdvertSlot(slotBudget.getSlotId());
		ParamAssert.isTrue(advertSlotDto == null, "广告位不存在");
		CooperationMode cooperationMode = CooperationMode.get(advertSlotDto.getCooperationMode());
		ParamAssert.isTrue(cooperationMode == null, "广告位合作模式枚举值无效");
		switch (cooperationMode) {
			case PD:
				boolean existMappingBySlotId = this.slotBudgetService.existMappingBySlotId(slotBudget.getSlotId());
				ParamAssert.isTrue(existMappingBySlotId, "PD模式广告位只能配置一条预算");
				ParamAssert.isTrue(slotBudget.getHasRta() != null && slotBudget.getHasRta(), "PD模式广告位不支持RTA，请关闭RTA配置重新提交");
				slotBudget.setSspFloorPrice(0);
				slotBudget.setDspFloorPrice(0);
				slotBudget.setSspPremiumRate(0);
				slotBudget.setDspFloatingRate(0);
				slotBudget.setHasRta(Boolean.FALSE.booleanValue());
				slotBudget.setRtaPriorityValue(0);
				slotBudget.setRtaSspFloorPrice(0);
				break;
			case RTB_MANUAL:
				ParamAssert.isTrue(slotBudget.getSspFloorPrice() == null || slotBudget.getSspFloorPrice() < 0, "媒体底价不能为空");
				ParamAssert.isTrue(slotBudget.getDspFloorPrice() == null || slotBudget.getDspFloorPrice() < 0, "上游底价不能为空");
				ParamAssert.isTrue(slotBudget.getDspFloorPrice() < slotBudget.getSspFloorPrice(), "上游底价必须大于媒体底价");
				ParamAssert.isTrue(slotBudget.getHasRta() == null, "是否启用RTA不能为空");
				if(slotBudget.getHasRta()) {
					ParamAssert.isTrue(slotBudget.getRtaPriorityValue() == null || slotBudget.getRtaPriorityValue() < 0, "RTA优先级值不能为空");
					ParamAssert.isTrue(slotBudget.getRtaSspFloorPrice() == null || slotBudget.getRtaSspFloorPrice() < 0, "RTA媒体底价不能为空");
				}else {
					slotBudget.setRtaPriorityValue(0);
					slotBudget.setRtaSspFloorPrice(0);
				}
				slotBudget.setSspPremiumRate(0);
				slotBudget.setDspFloatingRate(0);
				break;
			case RTB_AUTO:
				ParamAssert.isTrue(slotBudget.getSspPremiumRate() == null || slotBudget.getSspPremiumRate() < 0f, "溢价率不能为空");
				ParamAssert.isTrue(slotBudget.getDspFloatingRate() == null || slotBudget.getDspFloatingRate() < 0f, "底价上浮率不能为空");
				ParamAssert.isTrue(slotBudget.getHasRta() == null, "是否启用RTA不能为空");
				if(slotBudget.getHasRta()) {
					ParamAssert.isTrue(slotBudget.getRtaPriorityValue() == null || slotBudget.getRtaPriorityValue() < 0, "RTA优先级值不能为空");
					ParamAssert.isTrue(slotBudget.getRtaSspFloorPrice() == null || slotBudget.getRtaSspFloorPrice() < 0, "RTA媒体底价不能为空");
				}else {
					slotBudget.setRtaPriorityValue(0);
					slotBudget.setRtaSspFloorPrice(0);
				}
				slotBudget.setSspFloorPrice(0);
				slotBudget.setDspFloorPrice(0);
				break;
		}
		boolean existMapping = this.slotBudgetService.existMappingBySlotIdAndBudgetId(slotBudget.getSlotId(), slotBudget.getBudgetId());
		ParamAssert.isTrue(existMapping, "广告位预算映射已存在");
		
		DspBudgetDto budgetDto = this.dspBudgetService.getDspBudget(slotBudget.getBudgetId());
		ParamAssert.isTrue(budgetDto == null, "预算ID不存在");
		ParamAssert.isTrue(budgetDto.getDspPartnerId().intValue() != slotBudget.getDspPartnerId().intValue(), "预算ID与预算方ID不匹配");
		
		this.slotBudgetService.addSlotBudget(slotBudget);
		return new ManageResponse();
	}
	
	
	/**
	 * 更新广告预算
	 */
	@RequestMapping("/update")
	public ManageResponse update(@Validated SlotBudgetDto slotBudget) {
		ParamAssert.isTrue(slotBudget.getId() == null || slotBudget.getId() <= 0, "广告预算ID不能为空");
		ParamAssert.isTrue(slotBudget.getDspPartnerId() == null || slotBudget.getDspPartnerId() <= 0, "预算方ID不能为空");
		ParamAssert.isTrue(slotBudget.getBudgetId() == null || slotBudget.getBudgetId() <= 0, "预算ID不能为空");
		ParamAssert.isTrue(slotBudget.getSlotId() == null || slotBudget.getSlotId() <= 0, "广告位ID不能为空");
//		ParamAssert.isBlank(slotBudget.getDspAppId(), "预算方应用ID不能为空");
//		ParamAssert.isBlank(slotBudget.getDspSlotId(), "预算方广告位ID不能为空");
		ParamAssert.isTrue(slotBudget.getLimitType() == null || slotBudget.getLimitType() <= 0, "量级限制类型不能为空");
		ParamAssert.isTrue(slotBudget.getMaxRequests() == null || slotBudget.getMaxRequests() < -1, "每日最大请求数不能为空");
//		ParamAssert.isBlank(slotBudget.getPackageName(), "定向包名不能为空");
		RequestLimitType requestLimitType = RequestLimitType.get(slotBudget.getLimitType());
		ParamAssert.isTrue(requestLimitType == null, "量级限制类型枚举值无效");

		SspAdvertSlotDto advertSlotDto = this.sspAdvertSlotService.getSspAdvertSlot(slotBudget.getSlotId());
		ParamAssert.isTrue(advertSlotDto == null, "广告位不存在");
		CooperationMode cooperationMode = CooperationMode.get(advertSlotDto.getCooperationMode());
		ParamAssert.isTrue(cooperationMode == null, "广告位合作模式枚举值无效");
		switch (cooperationMode) {
			case PD:
				ParamAssert.isTrue(slotBudget.getHasRta() != null && slotBudget.getHasRta(), "PD模式广告不支持RTA，请关闭RTA配置重新提交");
				slotBudget.setSspFloorPrice(0);
				slotBudget.setDspFloorPrice(0);
				slotBudget.setSspPremiumRate(0);
				slotBudget.setDspFloatingRate(0);
				slotBudget.setHasRta(Boolean.FALSE.booleanValue());
				slotBudget.setRtaPriorityValue(0);
				slotBudget.setRtaSspFloorPrice(0);
				break;
			case RTB_MANUAL:
				ParamAssert.isTrue(slotBudget.getSspFloorPrice() == null || slotBudget.getSspFloorPrice() < 0, "媒体底价不能为空");
				ParamAssert.isTrue(slotBudget.getDspFloorPrice() == null || slotBudget.getDspFloorPrice() < 0, "上游底价不能为空");
				ParamAssert.isTrue(slotBudget.getDspFloorPrice() < slotBudget.getSspFloorPrice(), "上游底价必须大于媒体底价");
				ParamAssert.isTrue(slotBudget.getHasRta() == null, "是否启用RTA不能为空");
				if(slotBudget.getHasRta()) {
					ParamAssert.isTrue(slotBudget.getRtaPriorityValue() == null || slotBudget.getRtaPriorityValue() < 0, "RTA优先级值不能为空");
					ParamAssert.isTrue(slotBudget.getRtaSspFloorPrice() == null || slotBudget.getRtaSspFloorPrice() < 0, "RTA媒体底价不能为空");
				}else {
					SlotBudgetDto originalSlotBudget = this.slotBudgetService.getSlotBudget(slotBudget.getId());
					ParamAssert.isTrue(originalSlotBudget == null, "广告预算不存在");
					slotBudget.setRtaPriorityValue(originalSlotBudget.getRtaPriorityValue());
					slotBudget.setRtaSspFloorPrice(originalSlotBudget.getRtaSspFloorPrice());
				}
				slotBudget.setSspPremiumRate(0);
				slotBudget.setDspFloatingRate(0);
				break;
			case RTB_AUTO:
				ParamAssert.isTrue(slotBudget.getSspPremiumRate() == null || slotBudget.getSspPremiumRate() < 0f, "溢价率不能为空");
				ParamAssert.isTrue(slotBudget.getDspFloatingRate() == null || slotBudget.getDspFloatingRate() < 0f, "底价上浮率不能为空");
				ParamAssert.isTrue(slotBudget.getHasRta() == null, "是否启用RTA不能为空");
				if(slotBudget.getHasRta()) {
					ParamAssert.isTrue(slotBudget.getRtaPriorityValue() == null || slotBudget.getRtaPriorityValue() < 0, "RTA优先级值不能为空");
					ParamAssert.isTrue(slotBudget.getRtaSspFloorPrice() == null || slotBudget.getRtaSspFloorPrice() < 0, "RTA媒体底价不能为空");
				}else {
					SlotBudgetDto originalSlotBudget = this.slotBudgetService.getSlotBudget(slotBudget.getId());
					ParamAssert.isTrue(originalSlotBudget == null, "广告预算不存在");
					slotBudget.setRtaPriorityValue(originalSlotBudget.getRtaPriorityValue());
					slotBudget.setRtaSspFloorPrice(originalSlotBudget.getRtaSspFloorPrice());
				}
				slotBudget.setSspFloorPrice(0);
				slotBudget.setDspFloorPrice(0);
				break;
		}
		
		DspBudgetDto budgetDto = this.dspBudgetService.getDspBudget(slotBudget.getBudgetId());
		ParamAssert.isTrue(budgetDto == null, "预算ID不存在");
		ParamAssert.isTrue(budgetDto.getDspPartnerId().intValue() != slotBudget.getDspPartnerId().intValue(), "预算ID与预算方ID不匹配");
		
		this.slotBudgetService.updateSlotBudget(slotBudget);
		return new ManageResponse();
	}
	
	/**
	 * 删除广告位预算
	 */
	@RequestMapping("/delete")
	public ManageResponse delete(Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "广告位预算ID不能为空");
		this.slotBudgetService.deleteSlotBudget(id);
		return new ManageResponse();
	}
	
}
