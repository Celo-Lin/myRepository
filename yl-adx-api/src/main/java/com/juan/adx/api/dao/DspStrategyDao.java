package com.juan.adx.api.dao;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.juan.adx.common.cache.RedisKeyUtil;
import com.juan.adx.common.cache.RedisTemplate;
import com.juan.adx.model.dto.manage.DspStrategyDto;
import com.juan.adx.model.dto.manage.DspStrategyDtoList;
import com.juan.adx.model.entity.api.AdSlotWrap;
import com.juan.adx.model.entity.manage.DspPartnerStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class DspStrategyDao {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * DSP流量转发策略：第一层key: dsp的id， 第二层key为配置的code
     * @param slotId
     * @return
     */
    public Map<String, Map<String, List<DspStrategyDto>>> getAdSlotWrapById(Integer slotId) {
        Map<String, Map<String, List<DspStrategyDto>>> map = new HashMap<>();
        String key = RedisKeyUtil.getDspStrategyConfigKey(slotId);
        String value = this.redisTemplate.STRINGS.get(key);
        if (StringUtils.isBlank(value)) {
            return map;
        }

        List<DspStrategyDto> stgList = JSONArray.parseArray(value, DspStrategyDto.class);
        if (stgList == null || stgList.isEmpty()) {
            return map;
        }

        Map<Integer, List<DspStrategyDto>> mapList = stgList.stream().collect(Collectors.groupingBy(DspStrategyDto::getDspPartnerFid));
        for (Map.Entry<Integer, List<DspStrategyDto>> entry : mapList.entrySet()) {
           map.put(entry.getKey().toString(), entry.getValue().stream().collect(Collectors.groupingBy(DspStrategyDto::getCode)) );
        }
        return map;
    }

}
