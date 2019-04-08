package org.finalframework.json;

import lombok.NonNull;

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
@SuppressWarnings("unchecked")
public interface JsonService {

    /**
     * 将数据序列化为Json串
     *
     * @param object 数据对象
     * @return json 串
     * @throws Throwable 在序列化过程串出现的异常
     */
    String toJson(Object object) throws Throwable;

    default String toJson(Object object, @NonNull Class<?> view) throws Throwable {
        return toJson(object);
    }

    <T> T toObject(@NonNull String json, @NonNull Class<T> classOfT) throws Throwable;

    default <T> T toObject(@NonNull String json, @NonNull Class<T> classOfT, @NonNull Class<?> view) throws Throwable {
        return toObject(json, classOfT);
    }

    <T> T toObject(@NonNull String json, @NonNull Type typeOfT) throws Throwable;

    default <T> T toObject(@NonNull String json, @NonNull Type typeOfT, @NonNull Class<?> view) throws Throwable {
        return toObject(json, typeOfT);
    }

    default <E> List<E> toList(@NonNull String json, @NonNull Class<E> elementClass) throws Throwable {
        return toCollection(json, List.class, elementClass);
    }

    default <E> List<E> toList(@NonNull String json, @NonNull Class<E> elementClass, @NonNull Class<?> view) throws Throwable {
        return toCollection(json, List.class, elementClass, view);
    }

    default <E> Set<E> toSet(@NonNull String json, @NonNull Class<E> elementClass) throws Throwable {
        return toCollection(json, Set.class, elementClass);
    }

    default <E> Set<E> toSet(@NonNull String json, @NonNull Class<E> elementClass, @NonNull Class<?> view) throws Throwable {
        return toCollection(json, Set.class, elementClass, view);
    }

    <E, T extends Collection<E>> T toCollection(@NonNull String json, @NonNull Class<T> collectionClass, @NonNull Class<E> elementClass) throws Throwable;

    default <E, T extends Collection<E>> T toCollection(@NonNull String json, @NonNull Class<T> collectionClass, @NonNull Class<E> elementClass, @NonNull Class<?> view) throws Throwable {
        return toCollection(json, collectionClass, elementClass);
    }
}
