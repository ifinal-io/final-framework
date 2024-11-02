/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.data.mybatis.sql.util;

import org.springframework.util.ReflectionUtils;

import org.ifinalframework.data.mybatis.mapper.AbsMapper;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ProviderContextUtilTest.
 *
 * @author iimik
 * @version 1.2.2
 * @since 1.2.2
 */
class ProviderContextUtilTest {

    @Test
    void newContext() {
        final Method method = ReflectionUtils.findMethod(AbsMapper.class, "select");
        assertNotNull(ProviderContextUtil.newContext(AbsMapper.class, method));
    }
}
