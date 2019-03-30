package org.finalframework.mybatis.handler;

import org.finalframework.json.Json;

import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-20 11:20:53
 * @since 1.0
 */
public class JsonSetBlobTypeHandler<E> extends StringBlobTypeHandler<Set<E>> {
    private final Class<E> type;

    public JsonSetBlobTypeHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    protected Set<E> getNullableResult(String string) {
        return Json.toSet(string, type);
    }
}
