package org.ifinal.finalframework.mybatis.handler;


import org.ifinal.finalframework.json.Json;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class JsonListTypeHandler<T> extends AbsJsonTypeHandler<List<T>> {
    private final Class<T> type;

    public JsonListTypeHandler(@NonNull Class<T> type) {
        this.type = type;
    }

    @Override
    protected List<T> getNullableResult(String json) {
        return Json.toList(json, type);
    }
}
