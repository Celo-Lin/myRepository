package com.juan.adx.manage.service.adx;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.ExportExcelUtils;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.utils.NumberUtils;
import com.juan.adx.common.utils.PriceUtil;
import com.juan.adx.manage.dao.adx.DataStatisticsSlotDailyRealtimeDao;
import com.juan.adx.model.dto.manage.DataStatisticsSlotDailyRealtimeDto;
import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.form.manage.DataStatisticsSlotDailyRealtimeForm;

@Service
public class DataStatisticsSlotDailyRealtimeService {

	@Resource
	private DataStatisticsSlotDailyRealtimeDao dataStatisticsSlotDailyRealtimeDao;

	public PageData listDataStatementSlotDailyRealtime(DataStatisticsSlotDailyRealtimeForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<DataStatisticsSlotDailyRealtimeDto> dataList = this.dataStatisticsSlotDailyRealtimeDao.queryDataStatementSlotDailyRealtimeList(form);
        for (DataStatisticsSlotDailyRealtimeDto dataStatisticsSlotDailyRealtime : dataList) {
        	this.calcRatioAndEcpm(dataStatisticsSlotDailyRealtime);
		}
        PageInfo<DataStatisticsSlotDailyRealtimeDto> pageInfo = new PageInfo<DataStatisticsSlotDailyRealtimeDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}

	
	public void calcRatioAndEcpm(DataStatisticsSlotDailyRealtimeDto dataStatistics) {
		
		double fillRatio =  this.invalidValue(dataStatistics.getFillCount()) || this.invalidValue(dataStatistics.getRequestCount())
				? 0f : dataStatistics.getFillCount().doubleValue() / dataStatistics.getRequestCount().doubleValue() * 100; 
		fillRatio = NumberUtils.toScaledBigDecimal(fillRatio, 2, RoundingMode.HALF_UP);
		
		double displayRatio =  this.invalidValue(dataStatistics.getDisplayCount()) || this.invalidValue(dataStatistics.getFillCount())
				? 0f : dataStatistics.getDisplayCount().doubleValue() / dataStatistics.getFillCount().doubleValue() * 100; 
		displayRatio = NumberUtils.toScaledBigDecimal(displayRatio, 2, RoundingMode.HALF_UP);
		
		double clickRatio =  this.invalidValue(dataStatistics.getClickCount()) || this.invalidValue(dataStatistics.getDisplayCount())
				? 0f : dataStatistics.getClickCount().doubleValue() / dataStatistics.getDisplayCount().doubleValue() * 100; 
		clickRatio = NumberUtils.toScaledBigDecimal(clickRatio, 2, RoundingMode.HALF_UP);
		
		double downloadRatio =  this.invalidValue(dataStatistics.getDownloadCount()) || this.invalidValue(dataStatistics.getClickCount())
				? 0f : dataStatistics.getDownloadCount().doubleValue() / dataStatistics.getClickCount().doubleValue() * 100; 
		downloadRatio = NumberUtils.toScaledBigDecimal(downloadRatio, 2, RoundingMode.HALF_UP);
		
		double installRatio =  this.invalidValue(dataStatistics.getInstallCount()) || this.invalidValue(dataStatistics.getDownloadCount())
				? 0f : dataStatistics.getInstallCount().doubleValue() / dataStatistics.getDownloadCount().doubleValue() * 100; 
		installRatio = NumberUtils.toScaledBigDecimal(installRatio, 2, RoundingMode.HALF_UP);
		
		double deeplinkRatio =  this.invalidValue(dataStatistics.getDeeplinkCount()) || this.invalidValue(dataStatistics.getClickCount())
				? 0f : dataStatistics.getDeeplinkCount().doubleValue() / dataStatistics.getClickCount().doubleValue() * 100; 
		deeplinkRatio = NumberUtils.toScaledBigDecimal(deeplinkRatio, 2, RoundingMode.HALF_UP);

		//将预估收益金额单位转换为：元
		double sspEstimateIncome = PriceUtil.convertToActualAmount(dataStatistics.getSspEstimateIncome().longValue());

		//计算ssp ecpm
		double sspEcpm = PriceUtil.calcEcpm(dataStatistics.getSspEstimateIncome(), dataStatistics.getDisplayCount()); 
		
		//将预估收益金额单位转换为：元
		double dspEstimateIncome = PriceUtil.convertToActualAmount(dataStatistics.getDspEstimateIncome().longValue());

		//计算dsp ecpm
		double dspEcpm = PriceUtil.calcEcpm(dataStatistics.getDspEstimateIncome(), dataStatistics.getDisplayCount());

		
		dataStatistics.setFillRatio(fillRatio);
		dataStatistics.setDisplayRatio(displayRatio);
		dataStatistics.setClickRatio(clickRatio);
		dataStatistics.setDownloadRatio(downloadRatio);
		dataStatistics.setInstallRatio(installRatio);
		dataStatistics.setDeeplinkRatio(deeplinkRatio);
		dataStatistics.setSspEstimateIncome(sspEstimateIncome);
		dataStatistics.setDspEstimateIncome(dspEstimateIncome);
		dataStatistics.setSspEcpm(sspEcpm);
		dataStatistics.setDspEcpm(dspEcpm);
	}
	
	private boolean invalidValue(Integer value) {
		return value == null || value.intValue() <= 0;
	}


	public Workbook exportStatisticsData(DataStatisticsSlotDailyRealtimeForm form) {
		List<DataStatisticsSlotDailyRealtimeDto> dataList = this.dataStatisticsSlotDailyRealtimeDao.queryDataStatementSlotDailyRealtimeList(form);
		if(dataList == null || dataList.isEmpty()) {
			throw new ServiceRuntimeException(ExceptionEnum.NOT_FOUND_EXPORT_DATA);
		}
        for (DataStatisticsSlotDailyRealtimeDto dataStatisticsSlotDailyRealtime : dataList) {
        	this.calcRatioAndEcpm(dataStatisticsSlotDailyRealtime);
		}
        List<String> tableHeads = form.getTableHeads();
		for (int i = 0; CollectionUtils.isNotEmpty(tableHeads) && i < tableHeads.size(); i++) {
			String trimmedString = StringUtils.trimToEmpty(tableHeads.get(i));
			tableHeads.set(i, trimmedString);
		}
		
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (DataStatisticsSlotDailyRealtimeDto dataDto : dataList) {
			
			if(CollectionUtils.isEmpty(tableHeads)) {
				rowList.add(
					Arrays.asList(
							dataDto.getDspPartnerName(),
							dataDto.getSspPartnerName(),
							LocalDateUtils.formatSecondsToString(dataDto.getDate(), LocalDateUtils.DATE_FORMATTER),
							dataDto.getAppName(),
							dataDto.getAdSlotName(),
							String.valueOf(dataDto.getAdSlotId()),
							CooperationMode.get(dataDto.getCooperationMode()).getDesc(),
							dataDto.getBudgetName(),
							String.valueOf(dataDto.getMaxRequests()),
							String.valueOf(dataDto.getRequestCount()),
							String.valueOf(dataDto.getFillCount()),
							String.valueOf(dataDto.getDisplayCount()),
							String.valueOf(dataDto.getClickCount()),
							String.valueOf(dataDto.getDownloadCount()),
							String.valueOf(dataDto.getInstallCount()),
							String.valueOf(dataDto.getDeeplinkCount()),
							String.valueOf(dataDto.getFillRatio()),
							String.valueOf(dataDto.getDisplayRatio()),
							String.valueOf(dataDto.getClickRatio()),
							String.valueOf(dataDto.getDownloadRatio()),
							String.valueOf(dataDto.getInstallRatio()),
							String.valueOf(dataDto.getDeeplinkRatio()),
							String.valueOf(dataDto.getSspEstimateIncome()),
							String.valueOf(dataDto.getSspEcpm()),
							String.valueOf(dataDto.getDspEstimateIncome()),
							String.valueOf(dataDto.getDspEcpm())
							
				));
			}else {
				List<String> row = new ArrayList<String>();
				if(tableHeads.contains("预算方名称")) {
					row.add(dataDto.getDspPartnerName());
				}
				if(tableHeads.contains("流量方名称")) {
					row.add(dataDto.getSspPartnerName());
				}
				if(tableHeads.contains("时段")) {
					row.add(LocalDateUtils.formatSecondsToString(dataDto.getDate(), LocalDateUtils.DATE_FORMATTER));
				}
				if(tableHeads.contains("应用名称")) {
					row.add(dataDto.getAppName());
				}
				if(tableHeads.contains("广告位名称")) {
					row.add(dataDto.getAdSlotName());
				}
				if(tableHeads.contains("广告ID")) {
					row.add(String.valueOf(dataDto.getAdSlotId()));
				}
				if(tableHeads.contains("合作方式")) {
					String cooperationMode = CooperationMode.get(dataDto.getCooperationMode()).getDesc();
					row.add(cooperationMode);
				}
				if(tableHeads.contains("预算名称")) {
					row.add(dataDto.getBudgetName());
				}
				if(tableHeads.contains("限制量级")) {
					row.add(String.valueOf(dataDto.getMaxRequests()));
				}
				if(tableHeads.contains("请求数")) {
					row.add(String.valueOf(dataDto.getRequestCount()));
				}
				if(tableHeads.contains("填充数")) {
					row.add(String.valueOf(dataDto.getFillCount()));
				}
				if(tableHeads.contains("展示数")) {
					row.add(String.valueOf(dataDto.getDisplayCount()));
				}
				if(tableHeads.contains("点击数")) {
					row.add(String.valueOf(dataDto.getClickCount()));
				}
				if(tableHeads.contains("下载")) {
					row.add(String.valueOf(dataDto.getDownloadCount()));
				}
				if(tableHeads.contains("安装")) {
					row.add(String.valueOf(dataDto.getInstallCount()));
				}
				if(tableHeads.contains("dp")) {
					row.add(String.valueOf(dataDto.getDeeplinkCount()));
				}
				if(tableHeads.contains("填充率")) {
					row.add(String.valueOf(dataDto.getFillRatio()));
				}
				if(tableHeads.contains("曝光率")) {
					row.add(String.valueOf(dataDto.getDisplayRatio()));
				}
				if(tableHeads.contains("点击率")) {
					row.add(String.valueOf(dataDto.getClickRatio()));
				}
				if(tableHeads.contains("下载率")) {
					row.add(String.valueOf(dataDto.getDownloadRatio()));
				}
				if(tableHeads.contains("安装率")) {
					row.add(String.valueOf(dataDto.getInstallRatio()));
				}
				if(tableHeads.contains("dp率")) {
					row.add(String.valueOf(dataDto.getDeeplinkRatio()));
				}
				if(tableHeads.contains("渠道预估收益")) {
					row.add(String.valueOf(dataDto.getSspEstimateIncome()));
				}
				if(tableHeads.contains("渠道ecpm")) {
					row.add(String.valueOf(dataDto.getSspEcpm()));
				}
				if(tableHeads.contains("广告主预估收益")) {
					row.add(String.valueOf(dataDto.getDspEstimateIncome()));
				}
				if(tableHeads.contains("广告主ecpm")) {
					row.add(String.valueOf(dataDto.getDspEcpm()));
				}
				rowList.add(row);
			}
			
			
		}
		List<String> headers = new ArrayList<String>();
		headers.add("ID");
		headers.add("预算方名称");
		headers.add("流量方名称");
		headers.add("时段");
		headers.add("应用名称");
		headers.add("广告位名称");
		headers.add("广告ID");
		headers.add("合作方式");
		headers.add("预算名称");
		headers.add("限制量级");
		headers.add("请求数");
		headers.add("填充数");
		headers.add("展示数");
		headers.add("点击数");
		headers.add("下载");
		headers.add("安装");
		headers.add("dp");
		headers.add("填充率");
		headers.add("曝光率");
		headers.add("点击率");
		headers.add("下载率");
		headers.add("安装率");
		headers.add("dp率");
		headers.add("渠道预估收益");
		headers.add("渠道ecpm");
		headers.add("广告主预估收益");
		headers.add("广告主ecpm");
		
		if(CollectionUtils.isNotEmpty(tableHeads)) {
			Iterator<String> iterator = headers.iterator();
			while (iterator.hasNext()) {
				String value = iterator.next();
				if(!tableHeads.contains(value)) {
					iterator.remove();
				}
			}
		}
		for (int i = 0; i < headers.size(); i++) {
			if("时段".equals(headers.get(i))) {
				headers.set(i, "数据时段");
			}
			if("广告ID".equals(headers.get(i))) {
				headers.set(i, "广告位ID");
			}
		}
		
		Workbook workbook = new SXSSFWorkbook(dataList.size() + 1);
		ExportExcelUtils.exportExcelCommonHead(workbook, "时段数据统计", headers, 0, 0);
		ExportExcelUtils.exportExcelSheet(workbook, rowList ,0, 1);
		return workbook;
	}

}
