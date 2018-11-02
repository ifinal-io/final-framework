package cn.com.likly.finalframework.data.json;

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

    String toJson(Object object) throws Throwable;

    <T> T parse(@NonNull String json, @NonNull Class<T> classOfT) throws Throwable;

    <T> T parse(@NonNull String json, @NonNull Type typeOfT) throws Throwable;

    <E, T extends Collection<E>> T parse(@NonNull String json, @NonNull Class<T> collectionClass, @NonNull Class<E> elementClass) throws Throwable;
}
