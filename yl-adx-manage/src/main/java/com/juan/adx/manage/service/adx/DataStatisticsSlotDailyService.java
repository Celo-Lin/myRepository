package com.juan.adx.manage.service.adx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.ExportExcelUtils;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.utils.PriceUtil;
import com.juan.adx.manage.dao.adx.DataStatisticsSlotDailyDao;
import com.juan.adx.manage.dao.adx.DataStatisticsSlotDailySspDao;
import com.juan.adx.manage.dao.adx.SlotBudgetDao;
import com.juan.adx.model.dto.manage.DataStatisticsSlotDailyDto;
import com.juan.adx.model.dto.manage.ImportSlotDailyDto;
import com.juan.adx.model.entity.manage.DataStatisticsSlotDaily;
import com.juan.adx.model.entity.manage.DataStatisticsSlotDailySsp;
import com.juan.adx.model.entity.manage.SlotBudget;
import com.juan.adx.model.enums.AuditStatus;
import com.juan.adx.model.enums.CooperationMode;
import com.juan.adx.model.form.manage.DataStatisticsSlotDailyForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataStatisticsSlotDailyService {

	@Resource
	private DataStatisticsSlotDailyDao dataStatisticsSlotDailyDao;
	
	@Resource
	private DataStatisticsSlotDailySspDao dataStatisticsSlotDailySspDao;
	
	@Resource
	private SlotBudgetDao slotBudgetDao;

	public PageData listDataStatementSlotDaily(DataStatisticsSlotDailyForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<DataStatisticsSlotDailyDto> dataList = this.dataStatisticsSlotDailyDao.queryDataStatementSlotDailyList(form);
        for (DataStatisticsSlotDailyDto dataStatisticsSlot : dataList) {
			this.calcRatioAndEcpm(dataStatisticsSlot);
		}
        PageInfo<DataStatisticsSlotDailyDto> pageInfo = new PageInfo<DataStatisticsSlotDailyDto>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);	
	}

	public DataStatisticsSlotDailyDto getDataStatementSlotDaily(Integer id) {
		DataStatisticsSlotDailyDto dataStatisticsSlot = this.dataStatisticsSlotDailyDao.queryDataStatementSlotDaily(id);
		this.calcRatioAndEcpm(dataStatisticsSlot);
		return dataStatisticsSlot;
	}

	@Transactional(value = "adxTransactionManager")
	public void updateDataStatementSlotDaily(DataStatisticsSlotDailyDto slotDaily) {
		long sspEstimateIncome = PriceUtil.convertToEcpm(slotDaily.getSspEstimateIncome());
		long dspEstimateIncome = PriceUtil.convertToEcpm(slotDaily.getDspEstimateIncome());
		DataStatisticsSlotDaily dataStatisticsSlot = new DataStatisticsSlotDaily();
		dataStatisticsSlot.setId(slotDaily.getId());
		dataStatisticsSlot.setRequestCount(slotDaily.getRequestCount());
		dataStatisticsSlot.setFillCount(slotDaily.getFillCount());
		dataStatisticsSlot.setDisplayCount(slotDaily.getDisplayCount());
		dataStatisticsSlot.setClickCount(slotDaily.getClickCount());
		dataStatisticsSlot.setSspEstimateIncome(sspEstimateIncome);
		dataStatisticsSlot.setDspEstimateIncome(dspEstimateIncome);
		this.dataStatisticsSlotDailyDao.updateDataStatementSlotDaily(dataStatisticsSlot);
		
		//将每日统计报表的数据更新，同步到渠道报表中
		DataStatisticsSlotDailyDto originalSlotDailyDto = this.dataStatisticsSlotDailyDao.queryDataStatementSlotDaily(slotDaily.getId());
		DataStatisticsSlotDailySsp statisticsSlotDailySsp = new DataStatisticsSlotDailySsp();
		statisticsSlotDailySsp.setSspPartnerId(originalSlotDailyDto.getSspPartnerId());
		statisticsSlotDailySsp.setAdSlotId(originalSlotDailyDto.getAdSlotId());
		statisticsSlotDailySsp.setDate(originalSlotDailyDto.getDate());
		statisticsSlotDailySsp.setRequestCount(slotDaily.getRequestCount());
		statisticsSlotDailySsp.setFillCount(slotDaily.getFillCount());
		statisticsSlotDailySsp.setDisplayCount(slotDaily.getDisplayCount());
		statisticsSlotDailySsp.setClickCount(slotDaily.getClickCount());
		statisticsSlotDailySsp.setSspEstimateIncome(sspEstimateIncome);
		statisticsSlotDailySsp.setAuditStatus(AuditStatus.UNAUDITED.getStatus());
		this.dataStatisticsSlotDailySspDao.updateDataStatementSlotDailySsp(statisticsSlotDailySsp);
	}
	
	@Transactional(value = "adxTransactionManager")
	public void updatePdSlotEstimateIncome(ImportSlotDailyDto slotDailyDto) {
		
		this.dataStatisticsSlotDailyDao.updatePdSlotEstimateIncome(slotDailyDto);
		
		//将每日统计报表的PD广告收益数据更新，同步到渠道报表中
		DataStatisticsSlotDailyDto originalSlotDailyDto = this.dataStatisticsSlotDailyDao.queryDataStatementSlotDaily(slotDailyDto.getId());
		CooperationMode cooperationMode = CooperationMode.get(originalSlotDailyDto.getCooperationMode());
		if(cooperationMode != CooperationMode.PD) {
			return;
		}
		DataStatisticsSlotDailySsp statisticsSlotDailySsp = new DataStatisticsSlotDailySsp();
		statisticsSlotDailySsp.setSspPartnerId(originalSlotDailyDto.getSspPartnerId());
		statisticsSlotDailySsp.setAdSlotId(originalSlotDailyDto.getAdSlotId());
		statisticsSlotDailySsp.setDate(originalSlotDailyDto.getDate());
		statisticsSlotDailySsp.setSspEstimateIncome(slotDailyDto.getSspEstimateIncome());
		statisticsSlotDailySsp.setAuditStatus(AuditStatus.UNAUDITED.getStatus());
		this.dataStatisticsSlotDailySspDao.updatePdSlotEstimateIncomeSsp(statisticsSlotDailySsp);
	}
	
	
	
	private void calcRatioAndEcpm(DataStatisticsSlotDailyDto dataStatistics) {
		
		double fillRatio = PriceUtil.calcRatio(dataStatistics.getFillCount(), dataStatistics.getRequestCount());
		
		double displayRatio = PriceUtil.calcRatio(dataStatistics.getDisplayCount(), dataStatistics.getFillCount());
		
		double clickRatio = PriceUtil.calcRatio(dataStatistics.getClickCount(), dataStatistics.getDisplayCount());
		
		double downloadRatio = PriceUtil.calcRatio(dataStatistics.getDownloadCount(), dataStatistics.getClickCount());
		
		double installRatio = PriceUtil.calcRatio(dataStatistics.getInstallCount(), dataStatistics.getDownloadCount());
		
		double deeplinkRatio = PriceUtil.calcRatio(dataStatistics.getDeeplinkCount(), dataStatistics.getClickCount());

		//将预估收益金额单位转换为：元
		double sspEstimateIncome = PriceUtil.convertToActualAmount(dataStatistics.getSspEstimateIncome());

		//计算ssp ecpm
		double sspEcpm =  PriceUtil.calcEcpm(dataStatistics.getSspEstimateIncome(), dataStatistics.getDisplayCount());
		
		//将预估收益金额单位转换为：元
		double dspEstimateIncome = PriceUtil.convertToActualAmount(dataStatistics.getDspEstimateIncome());

		//计算dsp ecpm
		double dspEcpm = PriceUtil.calcEcpm(dataStatistics.getDspEstimateIncome(), dataStatistics.getDisplayCount());
		
		dataStatistics.setFillRatio(fillRatio);
		dataStatistics.setDisplayRatio(displayRatio);
		dataStatistics.setClickRatio(clickRatio);
		dataStatistics.setDownloadRatio(downloadRatio);
		dataStatistics.setInstallRatio(installRatio);
		dataStatistics.setDeeplinkRatio(deeplinkRatio);
		dataStatistics.setDspEstimateIncome(dspEstimateIncome);
		dataStatistics.setSspEstimateIncome(sspEstimateIncome);
		dataStatistics.setSspEcpm(sspEcpm);
		dataStatistics.setDspEcpm(dspEcpm);
	}
	
	
	public Workbook exportStatisticsData(DataStatisticsSlotDailyForm form) {
		List<DataStatisticsSlotDailyDto> dataList = this.dataStatisticsSlotDailyDao.queryDataStatementSlotDailyList(form);
		if(dataList == null || dataList.isEmpty()) {
			throw new ServiceRuntimeException(ExceptionEnum.NOT_FOUND_EXPORT_DATA);
		}
		for (DataStatisticsSlotDailyDto dataStatisticsSlot : dataList) {
			this.calcRatioAndEcpm(dataStatisticsSlot);
		}
		
		List<String> tableHeads = form.getTableHeads();
		for (int i = 0; CollectionUtils.isNotEmpty(tableHeads) && i < tableHeads.size(); i++) {
			String trimmedString = StringUtils.trimToEmpty(tableHeads.get(i));
			tableHeads.set(i, trimmedString);
		}
		
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (DataStatisticsSlotDailyDto dataDto : dataList) {
			
			/*//将预估收益金额单位转换为：元
			double sspEstimateIncome = PriceUtil.convertToActualAmount(dataDto.getSspEstimateIncome());
			//计算ssp ecpm
			double sspEcpm =  PriceUtil.calcEcpm(dataDto.getSspEstimateIncome(), dataDto.getDisplayCount());
			
			//将预估收益金额单位转换为：元
			double dspEstimateIncome = PriceUtil.convertToActualAmount(dataDto.getDspEstimateIncome());
			//计算dsp ecpm
			double dspEcpm = PriceUtil.calcEcpm(dataDto.getDspEstimateIncome(), dataDto.getDisplayCount()); */ 
			
			if(CollectionUtils.isEmpty(tableHeads)) {
				rowList.add(
					Arrays.asList(
							String.valueOf(dataDto.getId()),
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
				if(tableHeads.contains("ID")) {
					row.add(String.valueOf(dataDto.getId()));
				}
				if(tableHeads.contains("预算方名称")) {
					row.add(dataDto.getDspPartnerName());
				}
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
		headers.add("日期");
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
	
	public Workbook exportTemplateData(DataStatisticsSlotDailyForm form) {
		form.setCooperationMode(CooperationMode.PD.getMode());
		List<DataStatisticsSlotDailyDto> dataList = this.dataStatisticsSlotDailyDao.queryDataStatementSlotDailyList(form);
		if(dataList == null || dataList.isEmpty()) {
			throw new ServiceRuntimeException(ExceptionEnum.NOT_FOUND_EXPORT_DATA);
		}
		//过滤掉广告主预估收益、渠道预估收益值都大于0的记录
		Iterator<DataStatisticsSlotDailyDto> iterator = dataList.iterator();
		while (iterator.hasNext()) {
			DataStatisticsSlotDailyDto dataStatisticsSlotDailyDto = iterator.next();
			if(dataStatisticsSlotDailyDto.getSspEstimateIncome().longValue() > 0 
					&& dataStatisticsSlotDailyDto.getDspEstimateIncome().longValue() > 0) {
				iterator.remove();
			}
		}
		for (DataStatisticsSlotDailyDto dataStatisticsSlot : dataList) {
			this.calcRatioAndEcpm(dataStatisticsSlot);
		}
		List<List<String>> result = new ArrayList<List<String>>();
		for (DataStatisticsSlotDailyDto dataDto : dataList) {
			
			/*//将预估收益金额单位转换为：元
			double sspEstimateIncome = PriceUtil.convertToActualAmount(dataDto.getSspEstimateIncome());
			//将预估收益金额单位转换为：元
			double dspEstimateIncome = PriceUtil.convertToActualAmount(dataDto.getDspEstimateIncome());*/
			
			SlotBudget slotBudget = this.slotBudgetDao.querySlotBudgetByBudgetIdAndSlotId(dataDto.getAdSlotId(), dataDto.getBudgetId());
			String dspSlotId = Objects.nonNull(slotBudget) ? slotBudget.getDspSlotId() : "";
			result.add(
					Arrays.asList(
							String.valueOf(dataDto.getId()),
							dspSlotId,
							dataDto.getDspPartnerName(),
							dataDto.getSspPartnerName(),
							LocalDateUtils.formatSecondsToString(dataDto.getDate(), LocalDateUtils.DATE_FORMATTER),
							dataDto.getAppName(),
							String.valueOf(dataDto.getAdSlotId()),
							dataDto.getAdSlotName(),
							String.valueOf(dataDto.getBudgetId()),
							dataDto.getBudgetName(),
							String.valueOf(dataDto.getSspEstimateIncome()),
							String.valueOf(dataDto.getDspEstimateIncome())
					));
		}
		Workbook workbook = new SXSSFWorkbook(dataList.size() + 1);
		List<String> headers = new ArrayList<String>();
		headers.add("ID");
		headers.add("预算方广告位标识");
		headers.add("预算方名称");
		headers.add("流量方名称");
		headers.add("数据日期");
		headers.add("应用名称");
		headers.add("广告位ID");
		headers.add("广告位名称");
		headers.add("预算ID");
		headers.add("预算名称");
		headers.add("渠道预估收益");
		headers.add("广告主预估收益");
		List<String> enHeaders = new ArrayList<String>();
		enHeaders.add("ID");
		enHeaders.add("BUDGET_SLOT_ID");
		enHeaders.add("BUDGET_NAME");
		enHeaders.add("SSP_NAME");
		enHeaders.add("DATE");
		enHeaders.add("APP_NAME");
		enHeaders.add("SLOT_ID");
		enHeaders.add("SLOT_NAME");
		enHeaders.add("BUDGET_ID");
		enHeaders.add("BUDGET_NAME");
		enHeaders.add("SSP_ESTIMATE_INCOME");
		enHeaders.add("DSP_ESTIMATE_INCOME");
		result.add(0, enHeaders);
		ExportExcelUtils.exportExcelCommonHead(workbook, "PD广告收益", headers, 0, 0);
		ExportExcelUtils.exportExcelSheet(workbook, result ,0, 1);
		return workbook;
	}
	
	
	public void importSlotEstimateIncome(Workbook workbook) {
		try {
            List<ImportSlotDailyDto> slotDailyDtoList = new ArrayList<ImportSlotDailyDto>();
            for (int n = 0; n < workbook.getNumberOfSheets(); n++) {
				Sheet sheet = workbook.getSheetAt(n);
				if (sheet == null) {
					continue;
				}
				Row headerRow=sheet.getRow(1);//第一行
				for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
					Row row = sheet.getRow(i);
					Map<String,Object> map = new HashMap<String,Object>();
					for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
						Cell cell=row.getCell(j);
						String key = StringUtils.trimToEmpty(headerRow.getCell(j).getStringCellValue());
						CellType cellType = cell != null ? cell.getCellType() : CellType.BLANK ;
						switch (cellType){
							case STRING:
								map.put(key,StringUtils.trimToEmpty(row.getCell(j).getStringCellValue()));
								break;
							case NUMERIC:
								map.put(key,row.getCell(j).getNumericCellValue());
								break;
							case BOOLEAN:
								map.put(key,row.getCell(j).getBooleanCellValue());
								break;
							case FORMULA:
								map.put(key,row.getCell(j).getCellFormula());
								break;
							case BLANK:
								map.put(key,StringUtils.EMPTY);
								break;
						default:
							break;
						}
					}
					ImportSlotDailyDto slotDailyDto = convertMapToObject(map);
					slotDailyDtoList.add(slotDailyDto);
				}
			}
            for (ImportSlotDailyDto slotDailyDto : slotDailyDtoList) {
            	slotDailyDto.setCooperationMode(CooperationMode.PD.getMode());
            	this.updatePdSlotEstimateIncome(slotDailyDto);
			}
        } catch (Exception e) {
        	log.error("解析导入数据或更新数据异常.", e);
        	throw new ServiceRuntimeException(ExceptionEnum.UNCLASSIFIED.getCode(), "解析导入数据或更新数据失败，请联系管理员！");
		}
	}
	
	private ImportSlotDailyDto convertMapToObject(Map<String, Object> map) {
		ImportSlotDailyDto slotDailyDto = new ImportSlotDailyDto();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if(StringUtils.equalsIgnoreCase("ID", entry.getKey())) {
				
				String strValue = entry.getValue().toString();
				Double id = StringUtils.isNotBlank(strValue) ? Double.valueOf(strValue) : 0d;
				slotDailyDto.setId(id.intValue());
				
			}else if(StringUtils.equalsIgnoreCase("SSP_ESTIMATE_INCOME", entry.getKey())) {
				
				String strValue = entry.getValue().toString();
				Double value = StringUtils.isNotBlank(strValue) ? Double.valueOf(strValue) : 0d;
				long sspEstimateIncome = PriceUtil.convertToEcpm(value);
				slotDailyDto.setSspEstimateIncome(sspEstimateIncome);
				
			}else if(StringUtils.equalsIgnoreCase("DSP_ESTIMATE_INCOME", entry.getKey())) {
				
				String strValue = entry.getValue().toString();
				Double value = StringUtils.isNotBlank(strValue) ? Double.valueOf(strValue) : 0d;
				long dspEstimateIncome = PriceUtil.convertToEcpm(value);
				slotDailyDto.setDspEstimateIncome(dspEstimateIncome);
			}
			
		}
		return slotDailyDto;
	}
}
