/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.core.formatter.DateFormatterPattern;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-28 13:05:25
 * @since 1.0
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    public static final LocalDateSerializer INSTANCE = new LocalDateSerializer();
    private static final int ZONE_OFFSET = TimeZone.getDefault().getRawOffset() / 3600 / 1000;
    private static final String ZONE_OFFSET_ID = ZONE_OFFSET > 0 ? "+" + ZONE_OFFSET : "-" + ZONE_OFFSET;

    private DateTimeFormatter formatter;

    public LocalDateSerializer() {
        this.formatter = DateTimeFormatter.ofPattern(DateFormatterPattern.YYYY_MM_DD.getPattern());
    }

    public LocalDateSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void serialize(LocalDate localDate, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (localDate == null) return;
        if (formatter != null) {
            gen.writeString(localDate.format(formatter));
        } else {
            gen.writeNumber(localDate.toEpochDay());
        }
    }
}
