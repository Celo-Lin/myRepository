package com.juan.adx.task.config.database.alg.db;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Iterator;

/**
 * 分库算法
 */
public final class DatabaseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        String suffix = shardingValue.getValue().toUpperCase();
        for (String each : availableTargetNames) {
            if (each.endsWith(suffix)) {
                return each;
            }
        }
        String tableName = null;
        Iterator<String> iterator = availableTargetNames.iterator();
        if (iterator.hasNext()) {
            tableName = iterator.next();
        }
        String errorMsg = String.format("DatabaseShardingAlgorithm error:tableName:%s| suffix:%s", tableName, suffix);
        throw new UnsupportedOperationException(errorMsg);
    }
}