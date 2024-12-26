package com.juan.adx.task.job;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.juan.adx.task.schedule.AbstractJob;
import com.juan.adx.task.service.DataStatisticsSlotDailyService;
import com.juan.adx.task.service.DataStatisticsSlotDailySspService;
import com.juan.adx.task.service.SspPartnerService;
import com.juan.adx.common.cache.RedisKeyExpireTime;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.utils.NumberUtils;
import com.juan.adx.common.utils.PriceUtil;
import com.juan.adx.model.dto.manage.DataStatisticsIndexIncomeDto;
import com.juan.adx.model.dto.sspmanage.ChannelDataStatisticsIndexIncomeDto;
import com.juan.adx.model.entity.manage.SspPartner;

/**
 * 统计后台首页收益数据
 * 执行频率：按天（每天凌晨执行一次，T+1）
 */
@Component("statisticsIndexIncomeJob")
public class StatisticsIndexIncomeJob extends AbstractJob {

	@Resource
	private DataStatisticsSlotDailyService slotDailyService;
	
	@Resource
	private DataStatisticsSlotDailySspService slotDailySspService;
	
	@Resource
	private SspPartnerService sspPartnerService;
	
	@Resource
	private RedisTemplate redisTemplate;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		//统计内部后台首页收益
		this.statisticsIndexIncome();
		
		//统计渠道后台首页收益
		List<SspPartner> sspPartners = this.sspPartnerService.getSspPartnerAll();
		for (SspPartner sspPartner : sspPartners) {
			this.statisticsIndexIncomeSsp(sspPartner.getId());
		}
	}
	
	/**
	 * 统计渠道后台首页收益数据
	 */
	private void statisticsIndexIncomeSsp(Integer sspPartnerId) {
		//昨日预估收益
		LocalDate yesterdayLocalDate = LocalDateUtils.minusDays(1);
		long  yesterdaySeconds = LocalDateUtils.getStartSecondsByLocalDate(yesterdayLocalDate);
		long yesterdayIncome = this.slotDailySspService.statisticsSspEstimateIncome(sspPartnerId, yesterdaySeconds, yesterdaySeconds);
		//前天预估收益
		LocalDate beforedayLocalDate = LocalDateUtils.minusDays(2);
		long  beforedaySeconds = LocalDateUtils.getStartSecondsByLocalDate(beforedayLocalDate);
		long beforedayIncome = this.slotDailySspService.statisticsSspEstimateIncome(sspPartnerId, beforedaySeconds, beforedaySeconds);
		//近七天预估收益
		LocalDate sevenDayLocalDate = LocalDateUtils.minusDays(7);
		long  sevenDaySeconds = LocalDateUtils.getStartSecondsByLocalDate(sevenDayLocalDate);
		long sevenDayIncome = this.slotDailySspService.statisticsSspEstimateIncome(sspPartnerId, sevenDaySeconds, yesterdaySeconds);
		//本月预估收益
		LocalDate firstDayOfMonth = LocalDateUtils.getFirstDayOfMonth();
		long  firstDayOfMonthSeconds = LocalDateUtils.getStartSecondsByLocalDate(firstDayOfMonth);
		long monthIncome = this.slotDailySspService.statisticsSspEstimateIncome(sspPartnerId, firstDayOfMonthSeconds, yesterdaySeconds);
		
		//日环比
		double dailyRatio = 0d;
		if(beforedayIncome > 0) {
			dailyRatio = (yesterdayIncome - beforedayIncome) / (double) beforedayIncome * 100;
			dailyRatio = NumberUtils.toScaledBigDecimal(dailyRatio, 2, RoundingMode.HALF_UP);
		}
		
		//上个月同期预估收益
		LocalDate beforeMonthFirstDay = LocalDateUtils.getPreviousFirstDayOfMonth(1);
		LocalDate beforeMonthLastDay = LocalDateUtils.getPreviousMonthSameDay();
		long  beforeMonthFirstDaySeconds = LocalDateUtils.getStartSecondsByLocalDate(beforeMonthFirstDay);
		long  beforeMonthLastDaySeconds = LocalDateUtils.getStartSecondsByLocalDate(beforeMonthLastDay);
		long previousMonthIncome = this.slotDailySspService.statisticsSspEstimateIncome(sspPartnerId, beforeMonthFirstDaySeconds, beforeMonthLastDaySeconds);
		
		//月环比
		double monthRatio = 0d;
		if(previousMonthIncome > 0) {
			monthRatio = (monthIncome - previousMonthIncome) / (double) previousMonthIncome * 100;
			monthRatio = NumberUtils.toScaledBigDecimal(dailyRatio, 2, RoundingMode.HALF_UP);
		}
		
		double estimatedIncomeYesterday = PriceUtil.convertToActualAmount(yesterdayIncome);
		
		double estimatedIncomeSevenDay = PriceUtil.convertToActualAmount(sevenDayIncome);
		
		double estimatedIncomeMonth = PriceUtil.convertToActualAmount(monthIncome);
		
		ChannelDataStatisticsIndexIncomeDto channelIndexIncomeDto = new ChannelDataStatisticsIndexIncomeDto();
		channelIndexIncomeDto.setEstimatedIncomeYesterday(estimatedIncomeYesterday);
		channelIndexIncomeDto.setEstimatedIncomeYesterdayRatio(dailyRatio);
		channelIndexIncomeDto.setEstimatedIncomeSevenDay(estimatedIncomeSevenDay);
		channelIndexIncomeDto.setEstimatedIncomeMonth(estimatedIncomeMonth);
		channelIndexIncomeDto.setEstimatedIncomeMonthRatio(monthRatio);
		
		String todayStr = LocalDateUtils.formatLocalDateToString(LocalDate.now(), LocalDateUtils.DATE_PLAIN_FORMATTER);
		String key = RedisKeyUtil.getIndexIncomeSspKey(todayStr, sspPartnerId);
		this.redisTemplate.STRINGS.setEx(key, RedisKeyExpireTime.DAY_2, JSON.toJSONString(channelIndexIncomeDto));
	}

	public static void main(String[] args) {
		LocalDate sevenDayLocalDate = LocalDateUtils.minusDays(7);
		long  sevenDaySeconds = LocalDateUtils.getStartSecondsByLocalDate(sevenDayLocalDate);
		System.out.println(sevenDaySeconds);
	}
	
	/**
	 * 统计内部后台首页收益数据
	 */
	private void statisticsIndexIncome() {
		//昨日预估收益
		LocalDate yesterdayLocalDate = LocalDateUtils.minusDays(1);
		long  yesterdaySeconds = LocalDateUtils.getStartSecondsByLocalDate(yesterdayLocalDate);
		long yesterdayIncome = this.slotDailyService.statisticsDspEstimateIncome(yesterdaySeconds, yesterdaySeconds);
		//前天预估收益
		LocalDate beforedayLocalDate = LocalDateUtils.minusDays(2);
		long  beforedaySeconds = LocalDateUtils.getStartSecondsByLocalDate(beforedayLocalDate);
		long beforedayIncome = this.slotDailyService.statisticsDspEstimateIncome(beforedaySeconds, beforedaySeconds);
		//近七天预估收益
		LocalDate sevenDayLocalDate = LocalDateUtils.minusDays(7);
		long  sevenDaySeconds = LocalDateUtils.getStartSecondsByLocalDate(sevenDayLocalDate);
		long sevenDayIncome = this.slotDailyService.statisticsDspEstimateIncome(sevenDaySeconds, yesterdaySeconds);
		//本月预估收益
		LocalDate firstDayOfMonth = LocalDateUtils.getFirstDayOfMonth();
		long  firstDayOfMonthSeconds = LocalDateUtils.getStartSecondsByLocalDate(firstDayOfMonth);
		long monthIncome = this.slotDailyService.statisticsDspEstimateIncome(firstDayOfMonthSeconds, yesterdaySeconds);
		
		//日环比
		double dailyRatio = 0d;
		if(beforedayIncome > 0) {
			dailyRatio = (yesterdayIncome - beforedayIncome) / (double) beforedayIncome * 100;
			dailyRatio = NumberUtils.toScaledBigDecimal(dailyRatio, 2, RoundingMode.HALF_UP);
		}
		
		//上个月同期预估收益
		LocalDate beforeMonthFirstDay = LocalDateUtils.getPreviousFirstDayOfMonth(1);
		LocalDate beforeMonthLastDay = LocalDateUtils.getPreviousMonthSameDay();
		long  beforeMonthFirstDaySeconds = LocalDateUtils.getStartSecondsByLocalDate(beforeMonthFirstDay);
		long  beforeMonthLastDaySeconds = LocalDateUtils.getStartSecondsByLocalDate(beforeMonthLastDay);
		long previousMonthIncome = this.slotDailyService.statisticsDspEstimateIncome(beforeMonthFirstDaySeconds, beforeMonthLastDaySeconds);
		
		//月环比
		double monthRatio = 0d;
		if(previousMonthIncome > 0) {
			monthRatio = (monthIncome - previousMonthIncome) / (double) previousMonthIncome * 100;
			monthRatio = NumberUtils.toScaledBigDecimal(dailyRatio, 2, RoundingMode.HALF_UP);
		}
		
		double estimatedIncomeYesterday = PriceUtil.convertToActualAmount(yesterdayIncome);
		
		double estimatedIncomeSevenDay = PriceUtil.convertToActualAmount(sevenDayIncome);
		
		double estimatedIncomeMonth = PriceUtil.convertToActualAmount(monthIncome);
		
		DataStatisticsIndexIncomeDto indexIncomeDto = new DataStatisticsIndexIncomeDto();
		indexIncomeDto.setEstimatedIncomeYesterday(estimatedIncomeYesterday);
		indexIncomeDto.setEstimatedIncomeYesterdayRatio(dailyRatio);
		indexIncomeDto.setEstimatedIncomeSevenDay(estimatedIncomeSevenDay);
		indexIncomeDto.setEstimatedIncomeMonth(estimatedIncomeMonth);
		indexIncomeDto.setEstimatedIncomeMonthRatio(monthRatio);
		
		String todayStr = LocalDateUtils.formatLocalDateToString(LocalDate.now(), LocalDateUtils.DATE_PLAIN_FORMATTER);
		String key = RedisKeyUtil.getIndexIncomeKey(todayStr);
		this.redisTemplate.STRINGS.setEx(key, RedisKeyExpireTime.DAY_2, JSON.toJSONString(indexIncomeDto));
	}


}
