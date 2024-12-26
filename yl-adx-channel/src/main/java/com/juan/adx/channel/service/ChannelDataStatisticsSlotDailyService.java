package com.juan.adx.channel.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.channel.dao.ChannelDataStatisticsSlotDailyDao;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.ExportExcelUtils;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.utils.PriceUtil;
import com.juan.adx.model.dto.sspmanage.ChannelDataStatisticsSlotDailyDto;
import com.juan.adx.model.form.sspmanage.ChannelDataStatisticsSlotDailyForm;

@Service
public class ChannelDataStatisticsSlotDailyService {

	@Resource
	private ChannelDataStatisticsSlotDailyDao channelDataStatisticsSlotDailyDao;

	public PageData listSspDataStatementSlotDaily(ChannelDataStatisticsSlotDailyForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<ChannelDataStatisticsSlotDailyDto> dataList = this.channelDataStatisticsSlotDailyDao.querySspDataStatementSlotDailyList(form);
        for (ChannelDataStatisticsSlotDailyDto dataStatisticsSlotDailySsp : dataList) {
        	this.calcRatioAndEcpm(dataStatisticsSlotDailySsp);
		}
        PageInfo<ChannelDataStatisticsSlotDailyDto> pageInfo = new PageInfo<ChannelDataStatisticsSlotDailyDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}
	
	public void calcRatioAndEcpm(ChannelDataStatisticsSlotDailyDto dataStatistics) {
		
		double fillRatio = PriceUtil.calcRatio(dataStatistics.getFillCount(), dataStatistics.getRequestCount());
		
		double displayRatio = PriceUtil.calcRatio(dataStatistics.getDisplayCount(), dataStatistics.getFillCount());
		
		double clickRatio = PriceUtil.calcRatio(dataStatistics.getClickCount(), dataStatistics.getDisplayCount());
		
		double downloadRatio = PriceUtil.calcRatio(dataStatistics.getDownloadCount(), dataStatistics.getClickCount());
		
		double installRatio = PriceUtil.calcRatio(dataStatistics.getInstallCount(), dataStatistics.getDownloadCount());
		
		double deeplinkRatio = PriceUtil.calcRatio(dataStatistics.getDeeplinkCount(), dataStatistics.getClickCount());

		//将预估收益金额单位转换为：元
		double sspEstimateIncome = PriceUtil.convertToActualAmount(dataStatistics.getSspEstimateIncome().longValue());

		//计算ssp ecpm
		double sspEcpm = PriceUtil.calcEcpm(dataStatistics.getSspEstimateIncome(), dataStatistics.getDisplayCount()); 
		
		dataStatistics.setFillRatio(fillRatio);
		dataStatistics.setDisplayRatio(displayRatio);
		dataStatistics.setClickRatio(clickRatio);
		dataStatistics.setDownloadRatio(downloadRatio);
		dataStatistics.setInstallRatio(installRatio);
		dataStatistics.setDeeplinkRatio(deeplinkRatio);
		dataStatistics.setSspEstimateIncome(sspEstimateIncome);
		dataStatistics.setSspEcpm(sspEcpm);
	}
	
	
	public Workbook exportSspStatisticsData(ChannelDataStatisticsSlotDailyForm form) {
		List<ChannelDataStatisticsSlotDailyDto> dataList = this.channelDataStatisticsSlotDailyDao.querySspDataStatementSlotDailyList(form);
		if(dataList == null || dataList.isEmpty()) {
			throw new ServiceRuntimeException(ExceptionEnum.NOT_FOUND_EXPORT_DATA);
		}
		for (ChannelDataStatisticsSlotDailyDto sspDataStatisticsSlot : dataList) {
			this.calcRatioAndEcpm(sspDataStatisticsSlot);
		}
		List<List<String>> result = new ArrayList<List<String>>();
		for (ChannelDataStatisticsSlotDailyDto dataDto : dataList) {
			result.add(
					Arrays.asList(
							LocalDateUtils.formatSecondsToString(dataDto.getDate(), LocalDateUtils.DATE_FORMATTER),
							dataDto.getAppName(),
							dataDto.getAdSlotName(),
							String.valueOf(dataDto.getAdSlotId()),
//							String.valueOf(dataDto.getMaxRequests()),
							String.valueOf(dataDto.getRequestCount()),
							String.valueOf(dataDto.getFillCount()),
							String.valueOf(dataDto.getDisplayCount()),
							String.valueOf(dataDto.getClickCount()),
//							String.valueOf(dataDto.getDownloadCount()),
//							String.valueOf(dataDto.getInstallCount()),
//							String.valueOf(dataDto.getDeeplinkCount()),
							String.valueOf(dataDto.getFillRatio()),
							String.valueOf(dataDto.getDisplayRatio()),
							String.valueOf(dataDto.getClickRatio()),
//							String.valueOf(dataDto.getDownloadRatio()),
//							String.valueOf(dataDto.getInstallRatio()),
//							String.valueOf(dataDto.getDeeplinkRatio()),
							String.valueOf(dataDto.getSspEstimateIncome()),
							String.valueOf(dataDto.getSspEcpm())
					));
		}
		Workbook workbook = new SXSSFWorkbook(dataList.size() + 1);
		List<String> headers = new ArrayList<String>();
		headers.add("日期");
		headers.add("应用名称");
		headers.add("广告位名称");
		headers.add("广告ID");
//		headers.add("限制量级");
		headers.add("请求数");
		headers.add("填充数");
		headers.add("展示数");
		headers.add("点击数");
//		headers.add("下载数");
//		headers.add("安装数");
//		headers.add("dp数");
		headers.add("填充率");
		headers.add("曝光率");
		headers.add("点击率");
//		headers.add("下载率");
//		headers.add("安装率");
//		headers.add("dp率");
		headers.add("预估收益");
		headers.add("ecpm");
		ExportExcelUtils.exportExcelCommonHead(workbook, "流量方数据报表", headers, 0, 0);
		ExportExcelUtils.exportExcelSheet(workbook, result ,0, 1);
		return workbook;
	}
	
}
