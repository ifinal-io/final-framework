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
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.ifinalframework.auto.service.annotation.AutoService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoService(JsonSerializer.class)
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    public static final LocalDateSerializer INSTANCE = new LocalDateSerializer();

    private final DateTimeFormatter formatter;

    public LocalDateSerializer() {
        this(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDateSerializer(final DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void serialize(final LocalDate localDate, final JsonGenerator gen, final SerializerProvider serializers)
            throws IOException {

        if (localDate == null) {
            gen.writeNull();
            return;
        }
        if (formatter != null) {
            gen.writeString(localDate.format(formatter));
        } else {
            gen.writeNumber(localDate.toEpochDay());
        }
    }

    @Override
    public Class<LocalDate> handledType() {
        return LocalDate.class;
    }

}
