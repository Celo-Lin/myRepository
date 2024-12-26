package com.juan.adx.task.job;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.service.BidRecordService;
import com.juan.adx.task.service.RtbDisplayRecordService;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.entity.api.RtbDisplayRecord;
import com.juan.adx.model.enums.DisplayReportStatus;

/**
 * 根据RTB广告展示事件上报明细数据，更新RTB广告出价记录的展示上报状态
 * 1、查询RTB广告展示事件上报明细记录。
 * 2、根据RTB展示事件上报明细与RTB广告出价明细匹配，能匹配上，则更新RTB广告出价记录的展示事件上报状态
 * 执行频率：按分钟（每2分钟，执行一次）
 */
@Component("bidRecordDisplayStatusUpdateJob")
public class BidRecordDisplayStatusUpdateJob extends AbstractJob  {
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@Resource
	private BidRecordService bidRecordService;
	
	@Resource
	private RtbDisplayRecordService displayRecordService;
	
	private final static int BATCH_SIZE = 50;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		long startId = 0;
		while (true) {
			List<RtbDisplayRecord> displayRecords = this.displayRecordService.getDisplayRecord(startId, BATCH_SIZE);
			if(CollectionUtils.isEmpty(displayRecords)) {
				break;
			}
			int lastIndex = displayRecords.size() - 1;
			RtbDisplayRecord lastItem = displayRecords.get(lastIndex);
			startId = lastItem.getId();
			
			List<Long> deleteIds = this.updateBidRecordDisplayStatus(displayRecords);
			if(CollectionUtils.isNotEmpty(deleteIds)) {
				this.displayRecordService.deleteDisplayRecordByIds(deleteIds);
			}
		}
	}
	
	private List<Long> updateBidRecordDisplayStatus(List<RtbDisplayRecord> displayRecords) {
		List<Long> deleteIds = new ArrayList<Long>();
		for (RtbDisplayRecord rtbDisplayRecord : displayRecords) {
			LocalDate startLocal = LocalDateUtils.minusDays(2);
			LocalDate endLocal = LocalDateUtils.plusDays(1);
			
			long startSeconds = LocalDateUtils.getStartSecondsByLocalDate(startLocal);
			long endSeconds = LocalDateUtils.getEndSecondsByLocalDate(endLocal);
			boolean ret = this.bidRecordService.updateBidRecordDisplayStatus(rtbDisplayRecord.getTraceId(), DisplayReportStatus.REPORTED.getStatus(),
					startSeconds, endSeconds);
			if(ret) {
				deleteIds.add(rtbDisplayRecord.getId());
			}
		}
		return deleteIds;
	}
	
}
