package org.ifinal.finalframework.sharding.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

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
        props.put("algorithm-expression", "person_${age % 2}_${name}");
        algorithm.setProps(props);

        algorithm.init();


        Map<String, Collection<Comparable<?>>> shardingValues = new HashMap<>();

        shardingValues.put("name", Arrays.asList("xiaoming", "xiaohong"));
        shardingValues.put("age", Arrays.asList(10, 11));


        ComplexKeysShardingValue<Comparable<?>> shardingValue = new ComplexKeysShardingValue<>(null, shardingValues, null);
        Collection<String> tables = algorithm.doSharding(null, shardingValue);

        Assertions.assertEquals(4, tables.size());

        logger.info("{}", tables);


    }


}