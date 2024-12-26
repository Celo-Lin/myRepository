package com.juan.adx.task.config.database.alg.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

/**
 * 复合分表策略，支持对多个字段分片
 * 使用场景：SQL 语句中有>，>=, <=，<，=，IN 和 BETWEEN AND 等操作符。
 */
public class TableComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> shardingValue) {
        //log.info("collection:" + JSON.toJSONString(availableTargetNames) + ",shardingValues:" + JSON.toJSONString(shardingValue));
        //得到每个分片健对应的值
        Collection<String> orderIdValues = this.getShardingValue(shardingValue, "order_id");
        Collection<String> userIdValues = this.getShardingValue(shardingValue, "user_id");

        List<String> shardingSuffix = new ArrayList<>();
        // 对两个分片健同时取模的方式分库
        for (String userId : userIdValues) {
            for (String orderId : orderIdValues) {
                String suffix = userId.hashCode() % 2 + "_" + orderId.hashCode() % 2;
                for (String availableName : availableTargetNames) {
                    if (availableName.endsWith(suffix)) {
                        shardingSuffix.add(availableName);
                    }
                }
            }
        }
        
        String tableName = null;
        Iterator<String> iterator = availableTargetNames.iterator();
        if (iterator.hasNext()) {
            tableName = iterator.next();
        }
        String errorMsg = String.format("TableComplexShardingAlgorithm error:tableName:%s| userId:%s ", tableName, userIdValues.toArray());
        throw new UnsupportedOperationException(errorMsg);
    }
    
    private Collection<String> getShardingValue(ComplexKeysShardingValue<String> shardingValues, final String key) {
        Collection<String> valueSet = new ArrayList<>();
        Map<String, Collection<String>> columnNameAndShardingValuesMap = shardingValues.getColumnNameAndShardingValuesMap();
        if (columnNameAndShardingValuesMap.containsKey(key)) {
            valueSet.addAll(columnNameAndShardingValuesMap.get(key));
        }
        return valueSet;
    }

}