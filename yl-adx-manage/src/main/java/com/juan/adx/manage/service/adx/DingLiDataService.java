package com.juan.adx.manage.service.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.common.model.PageData;
import com.juan.adx.manage.dao.adx.DingLiDataDao;
import com.juan.adx.model.dto.manage.DingLiStatisticsDataDto;
import com.juan.adx.model.entity.manage.DingLiDataStatistics;
import com.juan.adx.model.entity.manage.DingLiDict;
import com.juan.adx.model.form.manage.DingLiDataStatisticsForm;

@Service
public class DingLiDataService {

	@Resource
	private DingLiDataDao dingLiDataDao;

	public PageData listDataStatement(DingLiDataStatisticsForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<DingLiStatisticsDataDto> dataList = this.dingLiDataDao.queryDataStatement(form);
        PageInfo<DingLiStatisticsDataDto> pageInfo = new PageInfo<DingLiStatisticsDataDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}

	public void saveDataStatement(DingLiDataStatistics dataStatistics) {
		this.dingLiDataDao.saveDataStatement(dataStatistics);
	}

	public List<DingLiDict> ads() {
		return this.dingLiDataDao.ads();
	}

	public List<DingLiDict> channels() {
		return this.dingLiDataDao.channels();
	}

	public void updateDataStatement(DingLiDataStatistics dataStatistics) {
		this.dingLiDataDao.updateDataStatement(dataStatistics);
	}

	public void deleteDataStatement(Integer id) {
		this.dingLiDataDao.deleteDataStatement(id);
	}
}
