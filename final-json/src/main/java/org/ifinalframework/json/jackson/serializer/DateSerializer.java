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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoService(JsonSerializer.class)
public class DateSerializer extends JsonSerializer<Date> {

    public static final DateSerializer INSTANCE = new DateSerializer();

    private final String pattern;

    public DateSerializer(final String pattern) {
        this.pattern = pattern;
    }

    public DateSerializer() {
        this(null);
    }

    @Override
    public void serialize(final Date date, final JsonGenerator gen, final SerializerProvider serializers)
        throws IOException {

        if (date == null) {
            gen.writeNull();
            return;
        }
        if (pattern != null) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormat.setTimeZone(LocaleContextHolder.getTimeZone());
            gen.writeString(simpleDateFormat.format(date));
        } else {
            gen.writeNumber(date.getTime());
        }
    }

    @Override
    public Class<Date> handledType() {
        return Date.class;
    }

}
