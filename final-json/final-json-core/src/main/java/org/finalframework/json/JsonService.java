package org.finalframework.json;

import lombok.NonNull;

import java.lang.reflect.Type;
import java.util.Collection;

/**
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
     * @throws Throwable 在序列化过程串出现的异常
     */
    String toJson(Object object) throws Throwable;

    default String toJson(Object object, @NonNull Class<?> view) throws Throwable {
        return toJson(object);
    }

    <T> T parse(@NonNull String json, @NonNull Class<T> classOfT) throws Throwable;

    default <T> T parseWithView(@NonNull String json, @NonNull Class<T> classOfT, @NonNull Class<?> view) throws Throwable {
        return parse(json, classOfT);
    }

    <T> T parse(@NonNull String json, @NonNull Type typeOfT) throws Throwable;

    default <T> T parseWithView(@NonNull String json, @NonNull Type typeOfT, @NonNull Class<?> view) throws Throwable {
        return parse(json, typeOfT);
    }

    <E, T extends Collection<E>> T parse(@NonNull String json, @NonNull Class<T> collectionClass, @NonNull Class<E> elementClass) throws Throwable;

    default <E, T extends Collection<E>> T parseWithView(@NonNull String json, @NonNull Class<T> collectionClass, @NonNull Class<E> elementClass, @NonNull Class<?> view) throws Throwable {
        return parse(json, collectionClass, elementClass);
    }
}
