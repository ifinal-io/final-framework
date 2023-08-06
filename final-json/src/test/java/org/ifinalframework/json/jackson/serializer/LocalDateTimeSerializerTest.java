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

package org.ifinalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * LocalDateTimeSerializerTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
class LocalDateTimeSerializerTest {

    @Test
    void serialize() {
        assertDoesNotThrow(() -> {
            LocalDateTimeSerializer serializer = new LocalDateTimeSerializer();

            assertEquals(LocalDateTime.class, serializer.handledType());

            JsonGenerator generator = mock(JsonGenerator.class);

            serializer.serialize(LocalDateTime.now(), generator, null);

            verify(generator, times(1)).writeNumber(anyLong());
            verify(generator, times(0)).writeString(anyString());

            serializer.serialize(null, generator, null);
            verify(generator, times(1)).writeNull();

        });
    }

    @Test
    void serializePattern() {
        assertDoesNotThrow(() -> {
            LocalDateTimeSerializer serializer = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

            assertEquals(LocalDateTime.class, serializer.handledType());

            JsonGenerator generator = mock(JsonGenerator.class);

            serializer.serialize(LocalDateTime.now(), generator, null);

            verify(generator, times(0)).writeNumber(anyLong());
            verify(generator, times(1)).writeString(anyString());

        });
    }

}
