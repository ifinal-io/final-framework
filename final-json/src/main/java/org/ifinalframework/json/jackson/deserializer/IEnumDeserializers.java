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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import org.ifinalframework.auto.service.annotation.AutoService;
import org.ifinalframework.core.IEnum;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * IEnumSerializers.
 *
 * @author ilikly
 * @version 1.0.0
 * @see org.ifinalframework.json.jackson.modifier.BeanEnumPropertySerializerModifier
 * @since 1.0.0
 */
@AutoService(Deserializers.class)
public class IEnumDeserializers extends SimpleDeserializers {

    @Override
    @SuppressWarnings("unchecked")
    public JsonDeserializer<?> findEnumDeserializer(final Class<?> type, final DeserializationConfig config,
                                                    final BeanDescription beanDesc)
            throws JsonMappingException {

        if (IEnum.class.isAssignableFrom(type)) {
            return new EnumDeserializer<>((Class<? extends IEnum<?>>) type);
        }

        return super.findEnumDeserializer(type, config, beanDesc);
    }

    /**
     * The {@link JsonDeserializer} for type of {@link Enum} which implementation the interface of {@link IEnum}.
     *
     * @author ilikly
     * @version 1.0.0
     * @since 1.0.0
     */
    private static class EnumDeserializer<T extends IEnum<?>> extends JsonDeserializer<T> {

        private final Class<T> enumType;

        private final Map<String, T> cache;

        EnumDeserializer(final Class<T> enumType) {
            this.enumType = Objects.requireNonNull(enumType, "the enumType must be not null!");
            this.cache = Arrays.stream(enumType.getEnumConstants())
                    .collect(Collectors.toMap(it -> it.getCode().toString(), Function.identity()));
        }

        @Override
        public T deserialize(final JsonParser p, final DeserializationContext context) throws IOException {
            return cache.get(p.getValueAsString());
        }

        public String toString() {
            return "EnumDeserializer{" + "enumType=" + enumType + '}';
        }

    }

}
