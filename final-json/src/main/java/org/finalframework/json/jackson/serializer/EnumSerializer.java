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
import org.finalframework.data.annotation.IEnum;

import java.io.IOException;

/**
 * 枚举{@link IEnum}对象序列化器，将枚举序列化成一个Json对象。
 *
 * <pre>
 *     <code>
 *         {
 *             "name" : "NAME",
 *             "code" : 1,
 *             "description" : "名称",
 *         }
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2019-08-26 14:37:49
 * @since 1.0
 */
public class EnumSerializer extends JsonSerializer<IEnum> {

    public static final EnumSerializer instance = new EnumSerializer();

    @Override
    public void serialize(IEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("code");
        gen.writeObject(value.getCode());
        if (value instanceof Enum) {
            gen.writeFieldName("name");
            gen.writeObject(((Enum) value).name());
        }
        gen.writeFieldName("description");
        gen.writeObject(value.getDesc());
        gen.writeEndObject();
    }
}
