package com.juan.adx.api.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.juan.adx.api.mapper.SysParameterMapper;
import com.juan.adx.model.entity.SysParameter;

@Repository
public class SysParameterDao {

    @Resource
    private SysParameterMapper sysParameterMapper;

    public List<SysParameter> queryAllSysParameter() {
        return this.sysParameterMapper.queryAllSysParameter();
    }
}
