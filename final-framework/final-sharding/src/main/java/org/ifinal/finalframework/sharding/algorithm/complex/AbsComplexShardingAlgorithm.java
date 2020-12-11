package org.ifinal.finalframework.sharding.algorithm.complex;


import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbsComplexShardingAlgorithm<T extends Comparable<?>> implements ComplexKeysShardingAlgorithm<T> {


    @Override
    public void init() {

    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<T> shardingValue) {

        Collection<String> result = new ArrayList<>();







        return result;
    }

    protected abstract Collection<String> doSharding(Collection<String> availableTargetNames, Collection<Map<String, T>> shardingValues);


    protected abstract boolean isAllowRangeQuery();

}
