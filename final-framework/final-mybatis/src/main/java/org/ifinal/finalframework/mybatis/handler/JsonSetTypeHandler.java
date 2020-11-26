package org.ifinal.finalframework.mybatis.handler;


import org.ifinal.finalframework.json.Json;

import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public class JsonSetTypeHandler<T> extends AbsJsonTypeHandler<Set<T>> {

    private final Class<T> type;

    public JsonSetTypeHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    protected Set<T> getNullableResult(String json) {
        return Json.toSet(json, type);
    }
}
