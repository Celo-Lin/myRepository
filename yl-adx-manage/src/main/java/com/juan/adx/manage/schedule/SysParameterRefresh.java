package com.juan.adx.manage.schedule;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.juan.adx.manage.config.ManageParameterConfig;
import com.juan.adx.manage.service.adx.SysParameterService;
import com.juan.adx.model.entity.SysParameter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SysParameterRefresh {
	
	@Resource
	private SysParameterService sysParameterService;

	public void exc() {
		try {
			List<SysParameter> parameters = this.sysParameterService.allSysParameters();
			if(parameters == null || parameters.isEmpty()) {
				return;
			}
			this.loadValue(parameters, new ManageParameterConfig());
			
			ManageParameterConfig paramConfig = new ManageParameterConfig();
		    Field[] fields = paramConfig.getClass().getDeclaredFields();
		    for( Field field : fields ){
		    	field.setAccessible(true);
		    	log.info("{}={}", field.getName(), field.get(paramConfig));
		    }
		    
		} catch (Exception e) {
			log.error("SysParameterTask load sys parmeter error.", e);
		}
	}

	
	private void loadValue(List<SysParameter> parameters, ManageParameterConfig config) {
		
		Map<String, String> map = new HashMap<String, String>();
		for (SysParameter pm : parameters) {
			String key = StringUtils.replace(pm.getKey(), ".", "");
			map.put(key.toUpperCase(), pm.getValue());
		}
		
		try {
			Field[] fields = config.getClass().getDeclaredFields();
			String value = "";
			for (Field field : fields) {
				if (field.getModifiers() != 9) {
					continue;
				}
				if (map.get(field.getName().toUpperCase()) == null) {
					continue;
				}
				value = map.get(field.getName().toUpperCase());
				field.setAccessible(true);
				if(field.getType() == Integer.class) {
					field.set(config, Integer.valueOf(value));
				}else if(field.getType() == Long.class) {
					field.set(config, Long.valueOf(value));
				}else if(field.getType() == String.class) {
					field.set(config, value);
				}else if(field.getType() == Double.class) {
					field.set(config, Double.valueOf(value));
				}else if(field.getType().getTypeName().equalsIgnoreCase("boolean")) {
					field.set(config, BooleanUtils.toBoolean(value));
				}
			}

		} catch (Exception e) {
			log.error("SysParameterTask load sys parameter error.", e);
		}
	}
}
