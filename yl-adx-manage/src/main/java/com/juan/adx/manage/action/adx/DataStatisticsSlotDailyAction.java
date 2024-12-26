package com.juan.adx.manage.action.adx;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.juan.adx.manage.service.adx.DataStatisticsSlotDailyService;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.dto.manage.DataStatisticsSlotDailyDto;
import com.juan.adx.model.form.manage.DataStatisticsSlotDailyForm;

import lombok.extern.slf4j.Slf4j;

/**
 * 广告位每日数据统计报表接口
 */
@Slf4j
@RestController
@RequestMapping("/adx/statistics/slot_daily")
public class DataStatisticsSlotDailyAction {

	@Resource
	private DataStatisticsSlotDailyService dataStatisticsSlotDailyService;
	
	/**
	 * 查询数据报表列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(DataStatisticsSlotDailyForm form) {
		PageData pageData = this.dataStatisticsSlotDailyService.listDataStatementSlotDaily(form);
		return new ManageResponse(pageData);
	}
	
	/**
	 * 查询数据报表详情
	 */
	@RequestMapping("/detail")
	public ManageResponse detail(Integer id) {
		ParamAssert.isTrue(id == null || id <= 0, "ID不能为空");
		DataStatisticsSlotDailyDto data = this.dataStatisticsSlotDailyService.getDataStatementSlotDaily(id);
		return new ManageResponse(data);
	}
	
	/**
	 * 更新数据报表
	 */
	@RequestMapping("/update")
	public ManageResponse update(DataStatisticsSlotDailyDto slotDaily) {
		ParamAssert.isTrue(slotDaily.getId() == null || slotDaily.getId() <= 0, "ID不能为空");
		ParamAssert.isTrue(slotDaily.getRequestCount() == null || slotDaily.getRequestCount() < 0, "请求数不能为空");
		ParamAssert.isTrue(slotDaily.getFillCount() == null || slotDaily.getFillCount() < 0, "填充数不能为空");
		ParamAssert.isTrue(slotDaily.getDisplayCount() == null || slotDaily.getDisplayCount() < 0, "展示数不能为空");
		ParamAssert.isTrue(slotDaily.getClickCount() == null || slotDaily.getClickCount() < 0, "点击数不能为空");
		ParamAssert.isTrue(slotDaily.getSspEstimateIncome() == null || slotDaily.getSspEstimateIncome() < 0, "渠道预估收益不能为空");
		ParamAssert.isTrue(slotDaily.getDspEstimateIncome() == null || slotDaily.getDspEstimateIncome() < 0, "广告主预估收益不能为空");
		this.dataStatisticsSlotDailyService.updateDataStatementSlotDaily(slotDaily);
		return new ManageResponse();
	}
	
	/**
	 * 导出数据报表
	 */
	@RequestMapping("/export")
    public void export(DataStatisticsSlotDailyForm form, HttpServletResponse response, HttpServletRequest request){
    	if( form.getStartTime() == null || form.getEndTime() == null ) {
    		throw new ServiceRuntimeException(ExceptionEnum.INVALID_PARAM.getCode(), "请输入导出时间，一次只允许导出31天内的数据");
    	}
    	Long durationDay = LocalDateUtils.getDurationDay(form.getStartTime(), form.getEndTime());
    	ParamAssert.isTrue(durationDay == null || durationDay.longValue() > 31, "一次只允许导出31天内的数据");
    	try {
    		Workbook workbook = dataStatisticsSlotDailyService.exportStatisticsData(form);
    		response.setHeader("Content-Disposition", "attachment; filename=slot-daily-statistic-"+ System.currentTimeMillis() + ".xlsx");
    		response.setContentType("application/octet-stream; charset=utf-8");
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
        	log.error("每日广告数据导出异常.", e);
        	throw new ServiceRuntimeException(ExceptionEnum.UNCLASSIFIED.getCode(), "数据报表导出失败，请联系管理员！");
        }
    }
	
	/**
	 * 导出PD收益模板，仅导出PD模式广告记录，用于导入PD模式广告收益数据
	 */
	@RequestMapping("/export_template")
	public void exportTemplate(DataStatisticsSlotDailyForm form, HttpServletResponse response, HttpServletRequest request){
		if( form.getStartTime() == null || form.getEndTime() == null ) {
			throw new ServiceRuntimeException(ExceptionEnum.INVALID_PARAM.getCode(), "请输入导出时间，一次只允许导出31天内的数据");
		}
		Long durationDay = LocalDateUtils.getDurationDay(form.getStartTime(), form.getEndTime());
		ParamAssert.isTrue(durationDay == null || durationDay.longValue() > 31, "一次只允许导出31天内的数据");
		try {
			Workbook workbook = dataStatisticsSlotDailyService.exportTemplateData(form);
			response.setHeader("Content-Disposition", "attachment; filename=slot-daily-template-"+ System.currentTimeMillis() + ".xlsx");
			response.setContentType("application/octet-stream; charset=utf-8");
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			log.error("PD收益模板导出异常.", e);
			throw new ServiceRuntimeException(ExceptionEnum.UNCLASSIFIED.getCode(), "PD收益模板导出失败，请联系管理员！");
		}
	}
	
	/**
	 * 导入PD收益数据
	 */
	@RequestMapping("/import")
    public ManageResponse importSlotEstimateIncomeData(@RequestParam("file") MultipartFile file){
		Workbook workbook;
		try {
			workbook = WorkbookFactory.create(file.getInputStream());
			this.dataStatisticsSlotDailyService.importSlotEstimateIncome(workbook);
		} catch (Exception e) {
			log.error("广告收益数据导入异常.", e);
        	throw new ServiceRuntimeException(ExceptionEnum.UNCLASSIFIED.getCode(), "收益数据导入失败，请联系管理员！");
		}
        return new ManageResponse();
    }
	
	
}
