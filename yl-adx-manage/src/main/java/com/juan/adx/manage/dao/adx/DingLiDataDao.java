package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.DingLiDataMapper;
import com.juan.adx.model.dto.manage.DingLiStatisticsDataDto;
import com.juan.adx.model.entity.manage.DingLiDataStatistics;
import com.juan.adx.model.entity.manage.DingLiDict;
import com.juan.adx.model.form.manage.DingLiDataStatisticsForm;

@Repository
public class DingLiDataDao {

	@Resource
	private DingLiDataMapper dingLiDataMapper;

	public List<DingLiStatisticsDataDto> queryDataStatement(DingLiDataStatisticsForm form) {
		return this.dingLiDataMapper.queryDataStatement(form);
	}

	public void saveDataStatement(DingLiDataStatistics dataStatistics) {
		this.dingLiDataMapper.saveDataStatement(dataStatistics);
	}

	public void updateDataStatement(DingLiDataStatistics dataStatistics) {
		this.dingLiDataMapper.updateDataStatement(dataStatistics);
	}

	public List<DingLiDict> channels() {
		return this.dingLiDataMapper.channels();
	}

	public List<DingLiDict> ads() {
		return this.dingLiDataMapper.ads();
	}

	public void deleteDataStatement(Integer id) {
		this.dingLiDataMapper.deleteDataStatement(id);
	}

}
