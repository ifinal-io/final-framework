package org.finalframework.mybatis.handler;


import org.finalframework.json.Json;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:55
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class JsonListTypeHandler<T> extends AbsJsonTypeHandler<List<T>> {
    private final Class<T> type;
    public JsonListTypeHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    protected List<T> getNullableResult(String json) {
        return Json.toList(json, type);
    }
}
