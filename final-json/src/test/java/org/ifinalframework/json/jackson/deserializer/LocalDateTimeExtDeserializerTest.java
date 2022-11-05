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

package org.ifinalframework.json.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * LocalDateTimeDeserializerTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class LocalDateTimeExtDeserializerTest {

    private final LocalDateTimeExtDeserializer deserializer = new LocalDateTimeExtDeserializer();

    @Mock
    private JsonParser jsonParser;

    @Mock
    private DeserializationContext context;

    @Test
    void deserialize() throws IOException {

        when(jsonParser.isNaN()).thenReturn(false);
        Date now = new Date();
        when(jsonParser.getValueAsLong()).thenReturn(now.getTime());

        LocalDateTime localDateTime = deserializer.deserialize(jsonParser, context);

        assertEquals(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), now.getTime());

    }

    @Test
    void deserializeSuper() throws IOException{
        when(jsonParser.isNaN()).thenReturn(true);
        when(jsonParser.hasTokenId(JsonTokenId.ID_STRING)).thenReturn(true);
        LocalDateTime now = LocalDateTime.now();
        when(jsonParser.getText()).thenReturn(now.toString());
        LocalDateTime localDateTime = deserializer.deserialize(jsonParser, context);
        Assertions.assertNotNull(localDateTime);
        assertEquals(now,localDateTime);
    }

    @Test
    void handledType() {
        assertEquals(LocalDateTime.class, deserializer.handledType());
    }

}
