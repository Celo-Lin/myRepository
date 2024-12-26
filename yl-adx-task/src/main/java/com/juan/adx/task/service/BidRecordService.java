package com.juan.adx.task.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.task.mapper.bidrecord.BidRecordMapper;
import com.juan.adx.model.entity.api.BidRecord;
import com.juan.adx.model.entity.task.StatisticsSlotIncomeDto;

@Service
public class BidRecordService {

	@Resource
	private BidRecordMapper bidRecordMapper;
	
	public boolean saveBidRecord(BidRecord bidRecord) {
		int ret = this.bidRecordMapper.saveBidRecord(bidRecord);
		return ret > 0;
	}

	public boolean updateBidRecordDisplayStatus(Long traceId, int status, long startSeconds, long endSeconds) {
		int ret = this.bidRecordMapper.updateBidRecordDisplayStatus(traceId, status, startSeconds, endSeconds);
		return ret > 0;
	}

	public List<StatisticsSlotIncomeDto> statisticsIncomeRealtime(long startSeconds, long endSeconds) {
		return this.bidRecordMapper.statisticsIncomeRealtime(startSeconds, endSeconds);
	}

	public List<StatisticsSlotIncomeDto> statisticsIncomeDaily(long startTime, long endTime) {
		return this.bidRecordMapper.statisticsIncomeDaily(startTime, endTime);
	}

	public List<StatisticsSlotIncomeDto> statisticsIncomeDailySsp(long startTime, long endTime) {
		return this.bidRecordMapper.statisticsIncomeDailySsp(startTime, endTime);
	}

}
