package org.finalframework.json;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Json 序列化与反序列化服务接口
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:24
 * @since 1.0
 */
public interface JsonService {

    /**
     * 将数据序列化为Json串
     *
     * @param object 数据对象
     * @return json 串
     * @throws Exception json exception
     */
    default String toJson(@Nullable Object object) throws Exception {
        return toJson(object, null);
    }

    /**
     * return the json string of {@linkplain Object object} with the {@linkplain Class view}.
     *
     * @param object the object
     * @param view   the view
     * @return the json string.
     * @throws Exception json exception
     */
    String toJson(@Nullable Object object, @Nullable Class<?> view) throws Exception;

    /**
     * return json {@linkplain Object value} of json string
     *
     * @param json     json string
     * @param classOfT value type
     * @param <T>      type
     * @return json value
     * @throws Exception json exception
     */
    default <T> T toObject(@Nullable String json, @Nullable Class<T> classOfT) throws Exception {
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
     * @throws Exception json exception
     */
    <T> T toObject(@Nullable String json, @NonNull Class<T> classOfT, @Nullable Class<?> view) throws Exception;

    /**
     * return json {@linkplain Object value} of json string
     *
     * @param json    json string
     * @param typeOfT value type
     * @param <T>     json type
     * @return json value
     * @throws Exception json exception
     */
    default <T> T toObject(@Nullable String json, @NonNull Type typeOfT) throws Exception {
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
     * @throws Exception json exception
     */
    <T> T toObject(@Nullable String json, @NonNull Type typeOfT, @Nullable Class<?> view) throws Exception;

    /**
     * return json {@linkplain List value} of json string
     *
     * @param json         json string
     * @param elementClass json element type
     * @param <E>          json type
     * @return json value
     * @throws Exception json exception
     */
    default <E> List<E> toList(@Nullable String json, @Nullable Class<E> elementClass) throws Exception {
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
     * @throws Exception json exception
     */
    default <E> List<E> toList(@Nullable String json, @Nullable Class<E> elementClass, @Nullable Class<?> view) throws Exception {
        return toCollection(json, List.class, elementClass, view);
    }

    /**
     * return json {@linkplain Set value} of json string
     *
     * @param json         json string
     * @param elementClass json element type
     * @param <E>          json type
     * @return json value
     * @throws Exception json exception
     */
    default <E> Set<E> toSet(@Nullable String json, @Nullable Class<E> elementClass) throws Exception {
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
     * @throws Exception json exception
     */
    default <E> Set<E> toSet(@Nullable String json, @Nullable Class<E> elementClass, @Nullable Class<?> view) throws Exception {
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
     * @throws Exception json exception
     */
    default <E, T extends Collection<E>> T toCollection(@Nullable String json, @Nullable Class<T> collectionClass, @Nullable Class<E> elementClass) throws Exception {
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
     * @return json value
     * @throws Exception json exception
     */
    <E, T extends Collection<E>> T toCollection(@Nullable String json, @Nullable Class<T> collectionClass, @Nullable Class<E> elementClass, @Nullable Class<?> view) throws Exception;
}
