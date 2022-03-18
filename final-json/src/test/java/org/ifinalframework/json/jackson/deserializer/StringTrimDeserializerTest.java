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

package org.ifinalframework.json.jackson.deserializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * StringTrimDeserializerTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
class StringTrimDeserializerTest {

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {" hah", "haha "})
    void deserialize(String value) throws IOException {
        StringTrimDeserializer deserializer = new StringTrimDeserializer();
        JsonParser jsonParser = mock(JsonParser.class);
        when(jsonParser.hasToken(JsonToken.VALUE_STRING)).thenReturn(true);
        when(jsonParser.getText()).thenReturn(value);
        assertEquals(value == null ? null : value.trim(),
            deserializer.deserialize(jsonParser, mock(DeserializationContext.class)));
    }

}
