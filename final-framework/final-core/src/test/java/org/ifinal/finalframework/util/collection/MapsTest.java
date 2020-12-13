package org.ifinal.finalframework.util.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class MapsTest {

    @Test
    void combine() {
        Map<String, Collection<Integer>> map = new HashMap<>();

        map.put("A", Arrays.asList(1, 2));
        map.put("B", Arrays.asList(3, 4));

        Collection<Map<String, Integer>> combine = Maps.combine(map);
        logger.info("{}", combine);
        assertEquals(4, combine.size());

        map.put("C", Arrays.asList(5, 6));
        combine = Maps.combine(map);
        logger.info("{}", combine);
        assertEquals(8, combine.size());

    }

}
