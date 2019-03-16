package org.finalframework.mybatis.handler;

import lombok.NonNull;
import org.finalframework.json.Json;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:48
 * @since 1.0
 */
public class JsonObjectBlobTypeHandler<T> extends StringBlobTypeHandler<T> {
    private final Class<T> type;

    public JsonObjectBlobTypeHandler(@NonNull Class<T> type) {
        this.type = type;
    }

    @Override
    protected T getNullableResult(String string) {
        if (string == null || string.isEmpty()) return null;
        return Json.parse(string, type);
    }
}
