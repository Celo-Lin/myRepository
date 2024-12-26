package com.juan.adx.manage.service.adx;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juan.adx.manage.dao.adx.SysParameterDao;
import com.juan.adx.model.entity.SysParameter;

@Service
public class SysParameterService {

	@Autowired
	private SysParameterDao sysParameterDao;
	
	public List<SysParameter> allSysParameters(){
		return this.sysParameterDao.allSysParameters();
	}
}
