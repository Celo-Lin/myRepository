package com.juan.adx.manage.config.alg;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 	取模分片，用于处理使用单一键作为分片键的=与IN进行分片的场景
 */
public final class ModuloPreciseTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

	/**
     * 	等值查询分表路由策略, 根据传入值取模返回正确表
     * @param availableTargetNames 可用表名
     * @param shardingValue 分片条件
     * @return 符合分片条件的表名
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        int size = availableTargetNames.size();
        String suffix = shardingValue.getValue().intValue() % size + "";
        for (String each : availableTargetNames) {
            if (each.endsWith(suffix)) {
                return each;
            }
        }
        String errorMsg = String.format("RealtimeStockTablePreciseShardingAlgorithm error:logicTableName:%s| shardingValue:%d", shardingValue.getLogicTableName(), shardingValue.getValue());
        throw new UnsupportedOperationException(errorMsg);
    }
}