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

package org.ifinalframework.json;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Json 序列化与反序列化服务接口
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JsonService {

    /**
     * 将数据序列化为Json串
     *
     * @param object 数据对象
     * @return json 串
     * @throws JsonException json JsonException
     */
    default String toJson(@Nullable Object object) {

        return toJson(object, null);
    }

    /**
     * return the json string of {@linkplain Object object} with the {@linkplain Class view}.
     *
     * @param object the object
     * @param view   the view
     * @return the json string.
     * @throws JsonException json JsonException
     */
    String toJson(@Nullable Object object, @Nullable Class<?> view);

    /**
     * return json {@linkplain Object value} of json string
     *
     * @param json     json string
     * @param classOfT value type
     * @param <T>      type
     * @return json value
     * @throws JsonException json JsonException
     */
    default <T> T toObject(@Nullable String json, @NonNull Class<T> classOfT) {

        return toObject(json, classOfT, null);
    }

    /**
     * return json {@linkplain Object value} of json string
     *
     * @param json     json string
     * @param classOfT value type
     * @param view     json view
     * @param <T>      json type
     * @return json value
     * @throws JsonException json JsonException
     */
    <T> T toObject(@Nullable String json, @NonNull Class<T> classOfT, @Nullable Class<?> view);

    /**
     * return json {@linkplain Object value} of json string
     *
     * @param json    json string
     * @param typeOfT value type
     * @param <T>     json type
     * @return json value
     * @throws JsonException json JsonException
     */
    default <T> T toObject(@Nullable String json, @NonNull Type typeOfT) {

        return toObject(json, typeOfT, null);
    }

    /**
     * return json {@linkplain Object value} of json string
     *
     * @param json    json string
     * @param typeOfT value type
     * @param view    json view
     * @param <T>     json type
     * @return json value
     * @throws JsonException json JsonException
     */
    <T> T toObject(@Nullable String json, @NonNull Type typeOfT, @Nullable Class<?> view);

    /**
     * return json {@linkplain List value} of json string
     *
     * @param json         json string
     * @param elementClass json element type
     * @param <E>          json type
     * @return json value
     * @throws JsonException json JsonException
     */
    @SuppressWarnings("unchecked")
    default <E> List<E> toList(@Nullable String json, @NonNull Class<E> elementClass) {

        return toCollection(json, List.class, elementClass, null);
    }

    /**
     * return json {@linkplain List value} of json string
     *
     * @param json         json string
     * @param elementClass json element type
     * @param view         json view
     * @param <E>          json type
     * @return json value
     * @throws JsonException json JsonException
     */
    @SuppressWarnings("unchecked")
    default <E> List<E> toList(@Nullable String json, @NonNull Class<E> elementClass, @Nullable Class<?> view) {

        return toCollection(json, List.class, elementClass, view);
    }

    /**
     * return json {@linkplain Set value} of json string
     *
     * @param json         json string
     * @param elementClass json element type
     * @param <E>          json type
     * @return json value
     * @throws JsonException json JsonException
     */
    @SuppressWarnings("unchecked")
    default <E> Set<E> toSet(@Nullable String json, @NonNull Class<E> elementClass) {

        return toCollection(json, Set.class, elementClass, null);
    }

    /**
     * return json {@linkplain Set value} of json string
     *
     * @param json         json string
     * @param elementClass json element type
     * @param view         json view
     * @param <E>          json type
     * @return json value
     * @throws JsonException json JsonException
     */
    @SuppressWarnings("unchecked")
    default <E> Set<E> toSet(@Nullable String json, @NonNull Class<E> elementClass, @Nullable Class<?> view) {

        return toCollection(json, Set.class, elementClass, view);
    }

    /**
     * return json {@linkplain Collection value} of json string
     *
     * @param json            json string
     * @param collectionClass json collection type
     * @param elementClass    json element type
     * @param <E>             json element type
     * @param <T>             json collection type
     * @return json value
     * @throws JsonException json JsonException
     */
    default <E, T extends Collection<E>> T toCollection(@Nullable String json, @NonNull Class<T> collectionClass,
        @NonNull Class<E> elementClass) {

        return toCollection(json, collectionClass, elementClass, null);
    }

    /**
     * return json {@linkplain Collection value} of json string
     *
     * @param json            json string
     * @param collectionClass json collection type
     * @param elementClass    json element type
     * @param <E>             json element type
     * @param <T>             json collection type
     * @param view            json view
     * @return json value
     * @throws JsonException json JsonException
     */
    <E, T extends Collection<E>> T toCollection(@Nullable String json, @NonNull Class<T> collectionClass,
        @NonNull Class<E> elementClass,
        @Nullable Class<?> view);

}
