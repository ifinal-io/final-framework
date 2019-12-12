package org.finalframework.mybatis.handler;

import org.finalframework.json.Json;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:48
 * @since 1.0
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
