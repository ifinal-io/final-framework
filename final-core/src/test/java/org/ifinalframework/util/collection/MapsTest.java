/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.util.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class MapsTest {

    @Test
    void combine() {
        final Map<String, Collection<Integer>> map = new HashMap<>();

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
