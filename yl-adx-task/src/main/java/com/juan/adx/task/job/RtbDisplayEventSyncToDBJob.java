package com.juan.adx.task.job;

import java.time.LocalDateTime;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.service.BidRecordService;
import com.juan.adx.task.service.RtbDisplayRecordService;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.entity.api.RtbDisplayRecord;
import com.juan.adx.model.enums.DisplayReportStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * 将RTB广告的展示事件上报明细数据保存到数据库
 * 执行频率：按分钟（每1分钟，执行一次）
 * 
 * 注意：重启时需要将此任务停止，并确保执行中的线程完毕
 * 
 * 1、先保存到adx_ad_rtb_display_record表中
 * 2、由 @BidRecordDisplayStatusUpdateJob 定时任务读adx_ad_rtb_display_record表中的数据，去adx_ad_bid_record表中做更新操作，
 * 	  将更新到的数据记录下来，一批执行完后将更新到的记录从adx_ad_rtb_display_record表中删除
 */
@Slf4j
@Component("rtbDisplayEventSyncToDBJob")
public class RtbDisplayEventSyncToDBJob extends AbstractJob {
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@Resource
	private BidRecordService bidRecordService;
	
	@Resource
	private RtbDisplayRecordService displayRecordService;
	
	
//	private void init() {
//		for (int i = 0; i < 10; i++) {
//			String key = RedisKeyUtil.getRtbReportDisplayRecordKey();
//			RtbDisplayRecord rtbDisplayRecord = new RtbDisplayRecord();
//			rtbDisplayRecord.setBudgetId(100 + i);
//			rtbDisplayRecord.setSlotId(100 + i);
//			rtbDisplayRecord.setTraceId(100l + i);
//			rtbDisplayRecord.setCtime(LocalDateUtils.getNowSeconds());
//			this.redisTemplate.LISTS.lpush(key, JSON.toJSONString(rtbDisplayRecord));
//		}
//	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		String key = RedisKeyUtil.getRtbReportDisplayRecordKey();
		while (true) {
			String value = this.redisTemplate.LISTS.rpop(key);
			if(StringUtils.isBlank(value)) {
				break;
			}
			try {
				RtbDisplayRecord rtbDisplayRecord = JSON.parseObject(value, RtbDisplayRecord.class);
				boolean updateRet = this.updateBidRecordDisplayStatus(rtbDisplayRecord.getTraceId(), rtbDisplayRecord.getCtime());
				if(updateRet) {
					continue;
				}
				boolean ret = this.displayRecordService.saveRtbDisplayRecord(rtbDisplayRecord);
				if(!ret) {
					log.warn("出价展示上报记录已存在，乎略本条记录， value: {}", value);
				}
			} catch (Exception e) {
				log.error("保存出价展示上报记录异常，value: {}", value);
				e.printStackTrace();
			}
		}
	}
	
	private boolean updateBidRecordDisplayStatus(Long traceId, Long ctime) {
		LocalDateTime dateTime = LocalDateUtils.parseSecondsToLocalDateTime(ctime);
		long startSeconds = LocalDateUtils.getStartSecondsByLocalDateTime(dateTime);
		long endSeconds = LocalDateUtils.getEndSecondsByLocalDateTime(dateTime);
		boolean ret = this.bidRecordService.updateBidRecordDisplayStatus(traceId, DisplayReportStatus.REPORTED.getStatus(), startSeconds, endSeconds);
		return ret;
	}
	
}
