package com.juan.adx.manage.action.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.adx.DictService;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.model.entity.manage.AppStore;
import com.juan.adx.model.entity.manage.Industry;

/**
 * 字典数据接口
 */
@RestController
@RequestMapping("/adx/dict")
public class DictAction {

	@Resource
	private DictService dictService;
	
	/**
	 * 查询应用商店字典数据
	 */
	@RequestMapping("/appstore")
	public ManageResponse appstore() {
		List<AppStore> data = this.dictService.listAppstore();
		return new ManageResponse(data);
	}
	
	/**
	 * 查询应用行业字典数据
	 */
	@RequestMapping("/industry")
	public ManageResponse industry() {
		List<Industry> data = this.dictService.listIndustry();
		return new ManageResponse(data);
	}
	
	
}
