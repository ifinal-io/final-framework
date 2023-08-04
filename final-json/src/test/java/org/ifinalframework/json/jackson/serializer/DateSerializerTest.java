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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * DateSerializerTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class DateSerializerTest {

    @Mock
    private JsonGenerator jsonGenerator;

    @Test
    void handleType() {
        DateSerializer dateSerializer = new DateSerializer();
        assertEquals(Date.class, dateSerializer.handledType());
    }

    @Test
    void shouldWriteNullWhenDateIsNull() throws IOException {
        DateSerializer dateSerializer = new DateSerializer();
        dateSerializer.serialize(null, jsonGenerator, null);
        verify(jsonGenerator, times(1)).writeNull();
    }

    @Test
    void shouldWriteNumberWhenDefault() throws IOException {
        DateSerializer dateSerializer = new DateSerializer();
        dateSerializer.serialize(new Date(), jsonGenerator, null);
        verify(jsonGenerator, times(1)).writeNumber(anyLong());
    }

    @Test
    void shouldWriteStringWhenPattern() throws IOException {
        LocaleContextHolder.setLocale(Locale.getDefault());
        LocaleContextHolder.setTimeZone(TimeZone.getDefault());
        String pattern = "yyyy-MM-dd";
        DateSerializer dateSerializer = new DateSerializer(pattern);
        Date date = new Date();
        String format = new SimpleDateFormat(pattern, LocaleContextHolder.getLocale()).format(date);
        dateSerializer.serialize(date, jsonGenerator, null);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(jsonGenerator, times(1)).writeString(argumentCaptor.capture());
        assertEquals(format, argumentCaptor.getValue());
    }

}
