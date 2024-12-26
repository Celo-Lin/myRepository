package com.juan.adx.manage.action.adx;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juan.adx.manage.service.adx.DataStatisticsSlotDailySspService;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.ManageResponse;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.common.validator.ParamAssert;
import com.juan.adx.model.enums.AuditStatus;
import com.juan.adx.model.form.manage.DataStatisticsSlotDailySspForm;

import lombok.extern.slf4j.Slf4j;

/**
 * 流量方广告位每日数据统计报表接口
 */
@Slf4j
@RestController
@RequestMapping("/adx/statistics/slot_daily_ssp")
public class DataStatisticsSlotDailySspAction {

	@Resource
	private DataStatisticsSlotDailySspService dataStatisticsSlotDailySspService;
	
	/**
	 * 查询流量方数据报表列表
	 */
	@RequestMapping("/list")
	public ManageResponse list(DataStatisticsSlotDailySspForm form) {
		PageData pageData = this.dataStatisticsSlotDailySspService.listDataStatementSlotDailySsp(form);
		return new ManageResponse(pageData);
	}
	
	/**
	 * 审核流量方数据报表
	 */
	@RequestMapping("/update/audit_status")
	public ManageResponse updateAuditStatus(Integer id, Integer status) {
		ParamAssert.isTrue(id == null || id <= 0, "ID不能为空");
		ParamAssert.isTrue(status == null || status <= 0, "审核状态不能为空");
		AuditStatus auditStatus = AuditStatus.get(status);
		ParamAssert.isTrue(auditStatus == null, "审核状态无效");
		this.dataStatisticsSlotDailySspService.updateAuditStatus(id, status);
		return new ManageResponse();
	}
	
	/**
	 * 批量审核流量方数据报表
	 */
	@RequestMapping("/update/batch_audit_status")
	public ManageResponse batchUpdateAuditStatus(@RequestParam("ids") List<Integer> ids, @RequestParam("status") Integer status) {
		ParamAssert.isTrue(ids == null || ids.isEmpty(), "ID列表不能为空");
		ParamAssert.isTrue(ids != null && ids.size() > 20, "ID列表大小不能超过20");
		ParamAssert.isTrue(status == null || status <= 0, "审核状态不能为空");
		AuditStatus auditStatus = AuditStatus.get(status);
		ParamAssert.isTrue(auditStatus == null, "审核状态无效");
		this.dataStatisticsSlotDailySspService.batchUpdateAuditStatus(ids, status);
		return new ManageResponse();
	}
	
	/**
	 * 导出流量方数据报表
	 */
	@RequestMapping("/export")
    public void export(DataStatisticsSlotDailySspForm form, HttpServletResponse response, HttpServletRequest request){
    	if( form.getStartTime() == null || form.getEndTime() == null ) {
    		throw new ServiceRuntimeException(ExceptionEnum.INVALID_PARAM.getCode(), "请输入导出时间，一次只允许导出31天内的数据");
    	}
    	Long durationDay = LocalDateUtils.getDurationDay(form.getStartTime(), form.getEndTime());
    	ParamAssert.isTrue(durationDay == null || durationDay.longValue() > 31, "一次只允许导出31天内的数据");
    	try {
    		Workbook workbook = dataStatisticsSlotDailySspService.exportSspStatisticsData(form);
    		response.setHeader("Content-Disposition", "attachment; filename=ssp-slot-daily-statistic-"+ System.currentTimeMillis() + ".xlsx");
    		response.setContentType("application/octet-stream; charset=utf-8");
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
        	log.error("ssp slot daily statistic export excel data error.", e);
        	throw new ServiceRuntimeException(ExceptionEnum.UNCLASSIFIED.getCode(), "对不起，流量方数据报表导出失败，请联系管理员！");
        }
    }
	
	
}
