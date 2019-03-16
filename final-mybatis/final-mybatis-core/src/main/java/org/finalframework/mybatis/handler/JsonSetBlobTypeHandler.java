package org.finalframework.mybatis.handler;

import org.finalframework.json.Json;

import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-20 11:20:53
 * @since 1.0
 */
public class JsonSetBlobTypeHandler<E> extends JsonCollectionBlobTypeHandler<E, Set<E>> {
    public JsonSetBlobTypeHandler(Class<E> type) {
        super(type);
    }

    @Override
    protected Set<E> getNullableResult(String json, Class type) {
        return Json.parse(json, Set.class, type);
    }
}
