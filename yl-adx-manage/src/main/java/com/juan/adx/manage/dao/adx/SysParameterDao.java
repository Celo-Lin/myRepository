package com.juan.adx.manage.dao.adx;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.juan.adx.manage.mapper.adx.SysParameterMapper;
import com.juan.adx.model.entity.SysParameter;

@Repository
public class SysParameterDao {

	@Autowired
	private SysParameterMapper sysParameterMapper;

	public List<SysParameter> allSysParameters() {
		return this.sysParameterMapper.allSysParameters();
	}
	
	
}
