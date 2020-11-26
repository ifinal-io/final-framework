package org.ifinal.finalframework.mybatis.handler;

import org.ifinal.finalframework.json.Json;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class JsonObjectTypeHandler<T> extends AbsJsonTypeHandler<T> {
    private final Class<T> type;

    public JsonObjectTypeHandler(@NonNull Class<T> type) {
        this.type = type;
    }

    @Override
    protected T getNullableResult(String json) {
        return Json.toObject(json, type);
    }
}
