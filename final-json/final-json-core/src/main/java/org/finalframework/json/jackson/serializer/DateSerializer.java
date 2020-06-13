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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-28 13:05:25
 * @since 1.0
 */
public class DateSerializer extends JsonSerializer<Date> {

    public static final DateSerializer INSTANCE = new DateSerializer();

    private final String pattern;

    public DateSerializer(String pattern) {
        this.pattern = pattern;
    }

    public DateSerializer() {
        this(DateFormatterPattern.YYYY_MM_DD_HH_MM_SS.getPattern());
    }

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (date == null) return;
        if (pattern != null) {
            gen.writeString(new SimpleDateFormat(pattern).format(date));
        } else {
            gen.writeNumber(date.getTime());
        }
    }
}
