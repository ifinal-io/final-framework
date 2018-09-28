package cn.com.likly.finalframework.json;

import lombok.NonNull;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:24
 * @since 1.0
 */
public interface JsonService {

    String toJson(Object object);

    <T> T parse(@NonNull String json, @NonNull Class<T> elementClass);

    <E, T extends Collection<E>> T parse(@NonNull String json, @NonNull Class<T> collectionClass, @NonNull Class<E> elementClass);
}
