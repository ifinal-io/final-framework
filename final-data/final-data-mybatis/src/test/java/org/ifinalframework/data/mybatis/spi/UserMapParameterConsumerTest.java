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

import org.ifinalframework.context.user.UserSupplier;
import org.ifinalframework.core.IUser;
import org.ifinalframework.data.annotation.AbsUser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * UserMapParameterConsumerTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@ExtendWith(MockitoExtension.class)
class UserMapParameterConsumerTest {

    @Mock
    private UserSupplier userSupplier;

    @Test
    void accept() {
        Map<String, Object> map = new LinkedHashMap<>();
        UserMapParameterConsumer consumer = new UserMapParameterConsumer(Collections.singletonList(userSupplier));
        // return null
        when(userSupplier.get()).thenReturn(null);
        consumer.accept(map, null, null);
        assertNull(map.get("USER"));


    }

    @Test
    void accept_not_null() {
        Map<String, Object> map = new LinkedHashMap<>();
        UserMapParameterConsumer consumer = new UserMapParameterConsumer(Collections.singletonList(userSupplier));
        final IUser absUser = new AbsUser();
        when(userSupplier.get()).thenReturn(absUser);

        consumer.accept(map, null, null);

        assertNotNull(map.get("USER"));
        assertEquals(absUser, map.get("USER"));
    }
}