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

package org.ifinalframework.data.spi;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * ConsumerTest.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@ExtendWith(MockitoExtension.class)
class ConsumerTest {

    @Spy
    private Consumer.ForEach consumer;

    @Test
    void accept() {
        consumer.accept(null, null, Arrays.asList(new Object()), null);
        verify(consumer, atLeastOnce()).accept(any(), any(), any(), any());
    }
}