package com.juan.adx.api.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.juan.adx.api.dao.SysParameterDao;
import com.juan.adx.model.entity.SysParameter;

@Service
public class SysParameterService {
	
	@Resource
	private SysParameterDao sysParameterDao;

	public List<SysParameter> allSysParameters() {
		return this.sysParameterDao.queryAllSysParameter();
	}

}
