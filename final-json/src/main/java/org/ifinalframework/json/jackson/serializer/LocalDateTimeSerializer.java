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

package org.ifinalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ifinalframework.auto.service.annotation.AutoService;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author likly
 * @version 1.0.0
 * @see LocaleContextHolder#getTimeZone()
 * @since 1.0.0
 */
@AutoService(JsonSerializer.class)
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private DateTimeFormatter formatter;

    public LocalDateTimeSerializer() {
    }

    public LocalDateTimeSerializer(final DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void serialize(final LocalDateTime localDateTime, final JsonGenerator gen,
        final SerializerProvider serializers) throws IOException {


        if (localDateTime == null) {
            gen.writeNull();
            return;
        }

        final ZoneId targetZoneId = LocaleContextHolder.getTimeZone().toZoneId();
        final LocalDateTime zoneLocalDateTime =
                localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(targetZoneId).toLocalDateTime();

        if (formatter != null) {
            gen.writeString(zoneLocalDateTime.format(formatter));
        } else {
            gen.writeNumber(zoneLocalDateTime.atZone(targetZoneId).toInstant().toEpochMilli());
        }
    }

    @Override
    public Class<LocalDateTime> handledType() {
        return LocalDateTime.class;
    }

}
