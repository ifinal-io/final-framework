/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.mybatis.spi;

import org.ifinalframework.data.annotation.AbsRecord;
import org.ifinalframework.data.mybatis.mapper.AbsMapper;
import org.ifinalframework.data.query.PageQuery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * QueryParameterConsumerTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
class QueryParameterConsumerTest {
    private final QueryParameterConsumer consumer = new QueryParameterConsumer();

    private interface AbsEntityMapper extends AbsMapper<Long, AbsRecord> {
    }

    @Test
    void accept() {
        final PageQuery query = new PageQuery();
        query.setOrders(Collections.singletonList("creator.id DESC"));

        query.setGroups(Collections.singletonList("id"));

        Map<String, Object> parameter = new LinkedHashMap<>();
        parameter.put("query", query);
        parameter.put(EntityClassParameterConsumer.ENTITY_CLASS_PARAM_NAME, AbsRecord.class);


        consumer.accept(parameter, AbsEntityMapper.class, null);
        final Object orders = parameter.get("orders");
        Assertions.assertNotNull(orders);
        Assertions.assertEquals(Arrays.asList("creator_id DESC"), orders);

        final Object groups = parameter.get("groups");
        Assertions.assertNotNull(groups);
        Assertions.assertEquals(Arrays.asList("id"), groups);


    }
}