package com.juan.adx.task.config.database.alg.table;

import java.time.LocalDate;
import java.util.Collection;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import com.juan.adx.common.utils.LocalDateUtils;

/**
 * ==和IN的分表算法实现
 */
public final class DateTimeTablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
    	LocalDate localDate = LocalDateUtils.parseSecondsToLocalDate(shardingValue.getValue());
        //LocalDate localDate = LocalDateUtils.formatStringToLocalDate(shardingValue.getValue(), LocalDateUtils.DATE_TIME_FORMATTER);
        String suffix = LocalDateUtils.formatLocalDateToString(localDate, LocalDateUtils.DATE_PLAIN_FORMATTER);
        for (String each : availableTargetNames) {
            if (each.endsWith(suffix)) {
                return each;
            }
        }
        String errorMsg = String.format("DateTimeTablePreciseShardingAlgorithm error:logicTableName:%s| shardingValue:%d", shardingValue.getLogicTableName(), shardingValue.getValue());
        throw new UnsupportedOperationException(errorMsg);
    }
}