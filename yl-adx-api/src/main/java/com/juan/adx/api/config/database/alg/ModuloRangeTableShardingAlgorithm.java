package com.juan.adx.api.config.database.alg;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 *	取模分片，用于处理使用单一键作为分片键的BETWEEN AND、>、<、>=、<=进行分片的场景
 *
 */
public final class ModuloRangeTableShardingAlgorithm implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        int size = collection.size();
        Collection<String> collect = new ArrayList<>();
        Range<Long> valueRange = rangeShardingValue.getValueRange();
        for (Long i = valueRange.lowerEndpoint(); i <= valueRange.upperEndpoint(); i++) {
            for (String each : collection) {
                if (each.endsWith(i % size + "")) {
                    collect.add(each);
                }
            }
        }
        return collect;
    }
}