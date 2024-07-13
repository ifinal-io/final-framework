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

package org.ifinalframework.json;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * 统一的Json调用入口 为常用的Json序列化与反序列化提供统一的入口。
 *
 * <h4>使用</h4>
 * <table>
 *     <thead>
 *         <tr>
 *             <td>场景</td>
 *             <td>方法</td>
 *             <td>示例</td>
 *         </tr>
 *     </thead>
 *     <tbody>
 *         <tr>
 *             <td>序列化</td>
 *             <td>{@link  #toJson(Object)}</td>
 *             <td>{@code Json.toJson(value) }</td>
 *         </tr>
 *         <tr>
 *             <td>视图序列化</td>
 *             <td>{@link #toJson(Object, Class)}</td>
 *             <td>{@code Json.toJson(value, view) }</td>
 *         </tr>
 *         <tr>
 *             <td>反序列化为{@code Object}</td>
 *             <td>{@link #toObject(String)}</td>
 *             <td>{@code Json.toObject(json)}</td>
 *         </tr>
 *     </tbody>
 * </table>
 *
 * <p>Json Serialized</p>
 * <pre class="code">
 * // to json
 * Json.toJson(object);
 * // to json with view
 * Json.toJson(object,view);
 * </pre>
 *
 * <h5>特性</h5>
 * <ul>
 *     <li>
 *         <h6>时间</h6>
 *         <p>对于时间类型的属性，在序列化时，自动追加{@code xxxFormat}的格式化属性，以增加更加直观化的可阅读性，支持的时间类型如下：</p>
 *         <ul>
 *             <li>{@link java.util.Date}</li>
 *             <li>{@link java.time.LocalDateTime}</li>
 *         </ul>
 *     </li>
 *     <li>
 *         <h6>枚举</h6>
 *         <p>对于{@link Enum}类型，在序列化时，自动追加{@code xxxName}的格式化属性，如果枚举实现了{@link org.ifinalframework.core.IEnum}接口，则还会自动追加{@code xxxDesc}属性。</p>
 *     </li>
 * </ul>
 *
 * @author iimik
 * @version 1.0.0
 * @see JsonService
 * @see JsonRegistry
 * @since 1.0.0
 */
public final class Json {

    private Json() {
        throw new AssertionError("No Json instances for you!");
    }

    /**
     * return the {@linkplain Object value} json string
     *
     * @param object the value
     * @return the json string
     * @throws JsonException json exception
     * @see #toJson(Object, Class)
     */
    public static String toJson(final @Nullable Object object) {

        return toJson(object, null);
    }

    /**
     * return json string of {@linkplain Object object} with the {@linkplain Class view}.
     *
     * @param object the object
     * @param view   the view
     * @return json string
     * @throws JsonException json exception
     */
    public static String toJson(final @Nullable Object object, final @Nullable Class<?> view) {
        if (object instanceof String) {
            return ((String) object).trim();
        }

        return wrap(() -> JsonRegistry.getInstance().getJsonService().toJson(object, view));

    }

    /**
     * return json value of {@linkplain String json}
     *
     * @param json json string
     * @return json value
     * @throws JsonException json exception
     */
    public static Object toObject(final @Nullable String json) {
        return toObject(json, Object.class);
    }

    /**
     * return the json value of {@linkplain String json}
     *
     * @param json     json string
     * @param classOfT json value type
     * @param <T>      json type
     * @return json value
     */
    public static <T> T toObject(final @Nullable String json, final @NonNull Class<T> classOfT) {
        return toObject(json, classOfT, null);
    }

    /**
     * return json value of {@linkplain String json}
     *
     * @param json     json string
     * @param classOfT json value type
     * @param view     json view
     * @param <T>      json type
     * @return json value
     * @throws JsonException json exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T toObject(final @Nullable String json, final @NonNull Class<T> classOfT, final @Nullable Class<?> view) {
        if (String.class.equals(classOfT)) {
            return (T) json;
        }

        return wrap(() -> JsonRegistry.getInstance().getJsonService().toObject(json, classOfT, view));
    }

    /**
     * return json {@linkplain Object value} of json {@linkplain String json}.
     *
     * @param json    json string
     * @param typeOfT json value type
     * @param <T>     json type
     * @return json value
     * @throws JsonException json exception
     */
    public static <T> T toObject(final @NonNull String json, final @NonNull TypeReference<T> typeOfT) {

        return toObject(json, typeOfT.getType());
    }

    /**
     * return json {@linkplain Object value} of json {@linkplain String json}.
     *
     * @param json    json string
     * @param typeOfT json value type
     * @param <T>     json type
     * @return json value
     * @throws JsonException json exception
     */
    public static <T> T toObject(final @NonNull String json, final @NonNull Type typeOfT) {
        return toObject(json, typeOfT, null);
    }

    /**
     * return json {@linkplain Object value} of json {@linkplain String json}.
     *
     * @param json    json string
     * @param typeOfT json value type
     * @param view    json view
     * @param <T>     json type
     * @return json value
     * @throws JsonException json exception
     */
    public static <T> T toObject(final @NonNull String json, final @NonNull TypeReference<T> typeOfT, @Nullable Class<?> view) {

        return toObject(json, typeOfT.getType(), view);
    }

    /**
     * return json {@linkplain Object value} of json {@linkplain String json}.
     *
     * @param json    json string
     * @param typeOfT json value type
     * @param view    json view
     * @param <T>     json type
     * @return json value
     * @throws JsonException json exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T toObject(final @NonNull String json, final @NonNull Type typeOfT, @Nullable Class<?> view) {
        if (String.class.equals(typeOfT)) {
            return (T) json;
        }

        return wrap(() -> JsonRegistry.getInstance().getJsonService().toObject(json, typeOfT, view));

    }

    /**
     * return json {@linkplain List list} of {@linkplain String json}.
     *
     * @param json     json string
     * @param classOfT json element value type
     * @param <E>      json element type
     * @return json list
     * @throws JsonException json exception
     */
    public static <E> List<E> toList(final @Nullable String json, final @NonNull Class<E> classOfT) {

        return toList(json, classOfT, null);
    }

    /**
     * return json {@linkplain List list} of {@linkplain String json}.
     *
     * @param json     json string
     * @param classOfT json element value type
     * @param view     json view
     * @param <E>      json element type
     * @return json list
     * @throws JsonException json exception
     */
    public static <E> List<E> toList(final @Nullable String json, final @NonNull Class<E> classOfT, final @Nullable Class<?> view) {
        return wrap(() -> JsonRegistry.getInstance().getJsonService().toList(json, classOfT, view));
    }

    /**
     * return json {@linkplain Set set} of json {@linkplain String json}
     *
     * @param json     json string
     * @param classOfT json element value type
     * @param <E>      json element type
     * @return json set
     * @throws JsonException json exception
     */
    public static <E> Set<E> toSet(final @Nullable String json, final @NonNull Class<E> classOfT) {
        return toSet(json, classOfT, null);
    }

    /**
     * return json {@linkplain Set set} of json {@linkplain String json}
     *
     * @param json     json string
     * @param classOfT json element value type
     * @param view     json view
     * @param <E>      json element type
     * @return json set
     * @throws JsonException json exception
     */
    public static <E> Set<E> toSet(final @Nullable String json, final @NonNull Class<E> classOfT, @Nullable Class<?> view) {
        return wrap(() -> JsonRegistry.getInstance().getJsonService().toSet(json, classOfT, view));
    }

    private static <T> T wrap(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (JsonException e) {
            throw e;
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

}
