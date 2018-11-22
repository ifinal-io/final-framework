package cn.com.likly.finalframework.mybatis.handler;

import lombok.NonNull;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:48
 * @since 1.0
 */
public abstract class JsonCollectionBlobTypeHandler<E, T extends Collection<E>> extends StringBlobTypeHandler<T> {
    private final Class<E> type;

    public JsonCollectionBlobTypeHandler(@NonNull Class<E> type) {
        this.type = type;
    }

    @Override
    protected final T getNullableResult(String string) {
        return getNullableResult(string, type);
    }

    protected abstract T getNullableResult(String json, Class type);
}
