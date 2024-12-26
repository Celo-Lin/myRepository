package com.juan.adx.task.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.bidrecord.RtbDisplayRecordMapper;
import com.juan.adx.model.entity.api.RtbDisplayRecord;

@Service
public class RtbDisplayRecordService {

	@Resource
	private RtbDisplayRecordMapper displayRecordMapper;

	public boolean saveRtbDisplayRecord(RtbDisplayRecord rtbDisplayRecord) {
		int ret = this.displayRecordMapper.saveRtbDisplayRecord(rtbDisplayRecord);
		return ret > 0;
	}

	public List<RtbDisplayRecord> getDisplayRecord(long startId, int batchSize) {
		return this.displayRecordMapper.queryDisplayRecord(startId, batchSize);
	}

	public void deleteDisplayRecordByIds(List<Long> ids) {
		this.displayRecordMapper.deleteDisplayRecordByIds(ids);
	}
}
