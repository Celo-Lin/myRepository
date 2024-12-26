package com.juan.adx.manage.action.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.manage.service.adx.DingLiDataService;
import com.juan.adx.model.entity.manage.DingLiDataStatistics;
import com.juan.adx.model.entity.manage.DingLiDict;
import com.juan.adx.model.form.manage.DingLiDataStatisticsForm;


/**
 * 丁莉程序化广告数据报表
 */
@RestController
@RequestMapping("/adx/statistics/dingli")
public class DingLiDataAction {

	@Resource
	private DingLiDataService dingLiDataService;
	
	/**
	 * 查询数据报表列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(DingLiDataStatisticsForm form) {
		PageData pageData = this.dingLiDataService.listDataStatement(form);
		return new ManageResponse(pageData);
	}
	
	/**
	 * 保存数据
	 */
	@RequestMapping("/save")
	public ManageResponse save(DingLiDataStatistics dataStatistics) {
		this.dingLiDataService.saveDataStatement(dataStatistics);
		return new ManageResponse();
	}
	
	/**
	 * 更新数据
	 */
	@RequestMapping("/update")
	public ManageResponse update(DingLiDataStatistics dataStatistics) {
		this.dingLiDataService.updateDataStatement(dataStatistics);
		return new ManageResponse();
	}
	
	/**
	 * 删除数据
	 */
	@RequestMapping("/delete")
	public ManageResponse delete(Integer id) {
		this.dingLiDataService.deleteDataStatement(id);
		return new ManageResponse();
	}

	@RequestMapping("/ads")
	public ManageResponse ads() {
		List<DingLiDict> dicts = this.dingLiDataService.ads();
		return new ManageResponse(dicts);
	}
	
	@RequestMapping("/channels")
	public ManageResponse channels() {
		List<DingLiDict> dicts = this.dingLiDataService.channels();
		return new ManageResponse(dicts);
	}
	
}
