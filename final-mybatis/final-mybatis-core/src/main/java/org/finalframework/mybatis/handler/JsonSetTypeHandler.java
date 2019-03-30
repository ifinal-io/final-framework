package org.finalframework.mybatis.handler;


import org.finalframework.json.Json;

import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:55
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class JsonSetTypeHandler<T> extends JsonTypeHandler<Set<T>> {

    private final Class<T> type;

    public JsonSetTypeHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    protected Set<T> getNullableResult(String json) {
        return Json.toSet(json, type);
    }
}
