package com.juan.adx.channel.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.channel.service.ChannelDictService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.model.entity.manage.AppStore;
import com.juan.adx.model.entity.manage.Industry;

/**
 * 字典数据接口
 */
@RestController
@RequestMapping("/dict")
public class ChannelDictAction {

	@Resource
	private ChannelDictService channelDictService;
	
	/**
	 * 查询应用商店字典数据
	 */
	@RequestMapping("/appstore")
	public ManageResponse appstore() {
		List<AppStore> data = this.channelDictService.listAppstore();
		return new ManageResponse(data);
	}
	
	/**
	 * 查询应用行业字典数据
	 */
	@RequestMapping("/industry")
	public ManageResponse industry() {
		List<Industry> data = this.channelDictService.listIndustry();
		return new ManageResponse(data);
	}
	
	
}
