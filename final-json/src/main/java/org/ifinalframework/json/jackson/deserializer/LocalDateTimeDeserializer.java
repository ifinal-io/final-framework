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
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.ifinalframework.auto.service.annotation.AutoService;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 * @see com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
 */
@AutoService(JsonDeserializer.class)
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(final JsonParser p, final DeserializationContext context) throws IOException {

        if (!p.isNaN()) {
            final long timestamp = p.getValueAsLong();
            final Instant instant = Instant.ofEpochMilli(timestamp);
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }

        return null;

    }

    @Override
    public Class<?> handledType() {
        return LocalDateTime.class;
    }

}
