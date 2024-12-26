package com.juan.adx.manage.service.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.juan.adx.manage.dao.adx.SspPartnerDao;
import com.juan.adx.common.exception.ExceptionEnum;
import com.juan.adx.common.exception.ServiceRuntimeException;
import com.juan.adx.common.model.PageData;
import com.juan.adx.common.utils.LocalDateUtils;
import com.juan.adx.model.dto.manage.SspPartnerOptionDto;
import com.juan.adx.model.entity.manage.SspPartner;
import com.juan.adx.model.enums.Status;
import com.juan.adx.model.form.manage.SspPartnerForm;

@Service
public class SspPartnerService {

	@Resource
	private SspPartnerDao sspPartnerDao;
	
	@Resource
	private SspAppService appService;

	public PageData listSspPartner(SspPartnerForm form) {
		PageHelper.startPage(form.getPageNo(), form.getPageSize());
        List<SspPartner> dataList = this.sspPartnerDao.querySspPartners(form);
        PageInfo<SspPartner> pageInfo = new PageInfo<SspPartner>(dataList);
		return new PageData().addPageInfo(pageInfo, dataList);		
	}

	public boolean addSspPartner(SspPartner sspPartner) {
		sspPartner.setStatus(Status.VALID.getStatus());
		long nowSeconds = LocalDateUtils.getNowSeconds();
		sspPartner.setCtime(nowSeconds);
		sspPartner.setUtime(nowSeconds);
		return this.sspPartnerDao.saveSspPartner(sspPartner) > 0;
	}

	public SspPartner getSspPartner(Integer id) {
		return this.sspPartnerDao.querySspPartner(id);
	}

	public boolean updateSspPartner(SspPartner sspPartner) {
		long nowSeconds = LocalDateUtils.getNowSeconds();
		sspPartner.setUtime(nowSeconds);
		return this.sspPartnerDao.updateSspPartner(sspPartner) > 0;
	}

	public void deleteSspPartner(Integer id) {
		boolean existAppMapping = this.appService.existAppByPartnerId(id);
		if(existAppMapping) {
			throw new ServiceRuntimeException(ExceptionEnum.MUST_DELETE_SSP_APP);
		}
		this.sspPartnerDao.deleteSspPartner(id);
	}

	public List<SspPartnerOptionDto> listSspPartnerOption(String name) {
		return this.sspPartnerDao.querySspPartnerOption(name);
	}
	
}
