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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.json.jackson.view.JsonViewValue;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-06 10:33:49
 * @since 1.0
 */
public class JsonViewValueSerializer extends JsonSerializer<JsonViewValue> {
    private final ObjectMapper objectMapper;

    public JsonViewValueSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void serialize(JsonViewValue value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        ObjectWriter objectWriter = objectMapper.writerWithView(value.getView());
        objectWriter.writeValue(gen, value.getValue());
    }
}

