package com.juan.adx.manage.service.adx;

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
import com.juan.adx.manage.dao.adx.DataStatisticsSlotDailySspDao;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.ExportExcelUtils;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.utils.PriceUtil;
import com.juan.adx.model.dto.manage.DataStatisticsSlotDailySspDto;
import com.juan.adx.model.form.manage.DataStatisticsSlotDailySspForm;

@Service
public class DataStatisticsSlotDailySspService {
	
	@Resource
	private DataStatisticsSlotDailySspDao dataStatisticsSlotDailySspDao;

	public PageData listDataStatementSlotDailySsp(DataStatisticsSlotDailySspForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<DataStatisticsSlotDailySspDto> dataList = this.dataStatisticsSlotDailySspDao.queryDataStatementSlotDailySspList(form);
        for (DataStatisticsSlotDailySspDto dataStatisticsSlotDailySsp : dataList) {
        	this.calcRatioAndEcpm(dataStatisticsSlotDailySsp);
		}
        PageInfo<DataStatisticsSlotDailySspDto> pageInfo = new PageInfo<DataStatisticsSlotDailySspDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}

	public void updateAuditStatus(Integer id, Integer status) {
		this.dataStatisticsSlotDailySspDao.updateAuditStatus(id, status);
	}

	public void batchUpdateAuditStatus(List<Integer> ids, Integer status) {
		for (Integer id : ids) {
			this.updateAuditStatus(id, status);
		}
	}

	
	private void calcRatioAndEcpm(DataStatisticsSlotDailySspDto dataStatistics) {
		
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
	
	
	public Workbook exportSspStatisticsData(DataStatisticsSlotDailySspForm form) {
		List<DataStatisticsSlotDailySspDto> dataList = this.dataStatisticsSlotDailySspDao.queryDataStatementSlotDailySspList(form);
		if(dataList == null || dataList.isEmpty()) {
			throw new ServiceRuntimeException(ExceptionEnum.NOT_FOUND_EXPORT_DATA);
		}
		for (DataStatisticsSlotDailySspDto sspDataStatisticsSlot : dataList) {
			this.calcRatioAndEcpm(sspDataStatisticsSlot);
		}
		
		List<String> tableHeads = form.getTableHeads();
		for (int i = 0; CollectionUtils.isNotEmpty(tableHeads) && i < tableHeads.size(); i++) {
			String trimmedString = StringUtils.trimToEmpty(tableHeads.get(i));
			tableHeads.set(i, trimmedString);
		}
		
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (DataStatisticsSlotDailySspDto dataDto : dataList) {
			if(CollectionUtils.isEmpty(tableHeads)) {
				rowList.add(
						Arrays.asList(
								dataDto.getSspPartnerName(),
								LocalDateUtils.formatSecondsToString(dataDto.getDate(), LocalDateUtils.DATE_FORMATTER),
								dataDto.getAppName(),
								dataDto.getAdSlotName(),
								String.valueOf(dataDto.getAdSlotId()),
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
								String.valueOf(dataDto.getSspEcpm())
				));
			}else {
				List<String> row = new ArrayList<String>();
				if(tableHeads.contains("流量方名称")) {
					row.add(dataDto.getSspPartnerName());
				}
				if(tableHeads.contains("日期")) {
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
				if(tableHeads.contains("下载数")) {
					row.add(String.valueOf(dataDto.getDownloadCount()));
				}
				if(tableHeads.contains("安装数")) {
					row.add(String.valueOf(dataDto.getInstallCount()));
				}
				if(tableHeads.contains("dp数")) {
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
				rowList.add(row);
			}
			
		}
		List<String> headers = new ArrayList<String>();
		headers.add("流量方名称");
		headers.add("日期");
		headers.add("应用名称");
		headers.add("广告位名称");
		headers.add("广告ID");
		headers.add("限制量级");
		headers.add("请求数");
		headers.add("填充数");
		headers.add("展示数");
		headers.add("点击数");
		headers.add("下载数");
		headers.add("安装数");
		headers.add("dp数");
		headers.add("填充率");
		headers.add("曝光率");
		headers.add("点击率");
		headers.add("下载率");
		headers.add("安装率");
		headers.add("dp率");
		headers.add("渠道预估收益");
		headers.add("渠道ecpm");
		
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
			if("日期".equals(headers.get(i))) {
				headers.set(i, "数据日期");
			}
			if("广告ID".equals(headers.get(i))) {
				headers.set(i, "广告位ID");
			}
		}
		
		Workbook workbook = new SXSSFWorkbook(dataList.size() + 1);
		ExportExcelUtils.exportExcelCommonHead(workbook, "数据报表", headers, 0, 0);
		ExportExcelUtils.exportExcelSheet(workbook, rowList ,0, 1);
		return workbook;
	}
}
