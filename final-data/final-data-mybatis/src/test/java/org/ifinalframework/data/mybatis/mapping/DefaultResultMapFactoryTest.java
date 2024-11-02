/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.data.mybatis.mapping;

import org.ifinalframework.data.annotation.AbsRecord;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * DefaultResultMapFactoryTest.
 *
 * @author iimik
 * @version 1.4.0
 * @since 1.4.0
 */
@Slf4j
class DefaultResultMapFactoryTest {
    private final DefaultResultMapFactory factory = new DefaultResultMapFactory();

    @Test
    void createFromAbsRecord() {
        ResultMap resultMap = factory.create(new Configuration(), AbsRecord.class);
        logger.info("{}", resultMap);
    }

    @Test
    void create() {
        ResultMap resultMap = factory.create(new Configuration(), MyRecord.class);
    }

    public static class MyRecord extends AbsRecord {
    }

    ;
}