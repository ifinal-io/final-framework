package org.ifinal.finalframework.sharding.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class ComplexInlineShardingAlgorithmTest {

    @Test
    void test() {

        ComplexInlineShardingAlgorithm algorithm = new ComplexInlineShardingAlgorithm();
        Properties props = new Properties();
        props.put("algorithm-expression", "person_${age % 2}_${type % 2}");
        props.put("sharding-columns", "age,type");
        algorithm.setProps(props);

        algorithm.init();

        Map<String, Collection<Comparable<?>>> shardingValues = new HashMap<>();

        shardingValues.put("type", Arrays.asList(1, 2));
        shardingValues.put("age", Arrays.asList(1, 2));

        Collection<String> availableTargetNames = Arrays.asList("person_0_0", "person_0_1", "person_1_0", "person_1_1");
        ComplexKeysShardingValue<Comparable<?>> shardingValue = new ComplexKeysShardingValue<>("person", shardingValues, null);
        Collection<String> tables = algorithm.doSharding(availableTargetNames, shardingValue);

        Assertions.assertEquals(4, tables.size());

        logger.info("{}", tables);

    }

}