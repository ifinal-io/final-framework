package org.finalframework.mybatis.handler;


import java.util.List;
import org.finalframework.json.Json;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:55
 * @since 1.0
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
