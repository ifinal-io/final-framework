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

package org.ifinalframework.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.json.JsonException;
import org.ifinalframework.json.JsonService;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Objects;

import lombok.Setter;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public class JacksonJsonService implements JsonService {

    @Setter
    private ObjectMapper objectMapper;

    public JacksonJsonService(final ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    public JacksonJsonService() {
        this(new ObjectMapperFactory().create());
    }

    @Override
    public String toJson(final @Nullable Object object, final @Nullable Class<?> view) {

        try {
            if (Objects.isNull(view)) {
                return objectMapper.writeValueAsString(object);
            }
            return objectMapper.writerWithView(view).writeValueAsString(object);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <T> T toObject(final @Nullable String json, final @NonNull Class<T> classOfT,
                          final @Nullable Class<?> view) {

        try {
            if (Objects.isNull(json)) {
                return null;
            }

            if (Objects.isNull(view)) {
                return objectMapper.readValue(json, classOfT);
            } else {
                return objectMapper.readerWithView(view).forType(classOfT).readValue(json);
            }
        } catch (Exception e) {
            throw new JsonException(e);
        }

    }

    @Override
    public <T> T toObject(final @Nullable String json, final @NonNull Type typeOfT, final @Nullable Class<?> view) {

        try {
            if (Objects.isNull(json)) {
                return null;
            }

            if (Objects.isNull(view)) {
                return objectMapper.readValue(json, objectMapper.getTypeFactory().constructType(typeOfT));
            } else {
                return objectMapper.readerWithView(view)
                        .forType(objectMapper.getTypeFactory().constructType(typeOfT))
                        .readValue(json);
            }
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <E, T extends Collection<E>> T toCollection(final @Nullable String json,
                                                       final @NonNull Class<T> collectionClass,
                                                       final @NonNull Class<E> elementClass, final @Nullable Class<?> view) {

        try {
            if (Objects.isNull(json)) {
                return null;
            }

            if (Objects.isNull(view)) {
                return objectMapper.readValue(json,
                        objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass));
            } else {
                return objectMapper.readerWithView(view)
                        .forType(objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass))
                        .readValue(json);
            }
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

}
