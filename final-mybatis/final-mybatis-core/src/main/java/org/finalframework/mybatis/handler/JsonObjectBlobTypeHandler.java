package org.finalframework.mybatis.handler;

import lombok.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:48
 * @since 1.0
 */
@Deprecated
public class JsonObjectBlobTypeHandler<T> extends StringBlobTypeHandler<T> {
    private final Class<T> type;

    public JsonObjectBlobTypeHandler(@NonNull Class<T> type) {
        this.type = type;
    }

    @Override
    protected T getNullableResult(String string) {
        if (string == null || string.isEmpty()) return null;
        return MybatisJson.toObject(string, type);
    }
}
