package com.juan.adx.manage.dao.adx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.SspAdvertSlotMapper;
import com.juan.adx.model.dto.manage.SspAdvertSlotDto;
import com.juan.adx.model.entity.manage.SspAdvertSlot;
import com.juan.adx.model.form.manage.SspAdvertSlotForm;

@Repository
public class SspAdvertSlotDao {
	
	@Resource
	private SspAdvertSlotMapper sspAdvertSlotMapper;

	public int countSlotByAppid(Integer appId) {
		return this.sspAdvertSlotMapper.countSlotByAppid(appId);
	}

	public List<SspAdvertSlotDto> querySspAdvertSlotList(SspAdvertSlotForm form) {
		return this.sspAdvertSlotMapper.querySspAdvertSlotList(form);
	}

	public int saveSspAdvertSlot(SspAdvertSlot advertSlot) {
		return this.sspAdvertSlotMapper.saveSspAdvertSlot(advertSlot);
	}

	public SspAdvertSlotDto querySspAdvertSlot(Integer id) {
		return this.sspAdvertSlotMapper.querySspAdvertSlot(id);
	}

	public int updateSspAdvertSlot(SspAdvertSlot advertSlot) {
		return this.sspAdvertSlotMapper.updateSspAdvertSlot(advertSlot);
	}
	
	public int syncChannelUpdateToSspAdvertSlot(SspAdvertSlot advertSlot) {
		return this.sspAdvertSlotMapper.syncChannelUpdateToSspAdvertSlot(advertSlot);
	}

	public int updateSspAdvertSlotStatus(Integer id, Integer status) {
		return this.sspAdvertSlotMapper.updateSspAdvertSlotStatus(id, status);
	}

	public int deleteSspAdvertSlot(Integer id) {
		return this.sspAdvertSlotMapper.deleteSspAdvertSlot(id);
	}

}
