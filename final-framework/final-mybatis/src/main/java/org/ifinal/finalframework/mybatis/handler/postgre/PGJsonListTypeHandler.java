package org.ifinal.finalframework.mybatis.handler.postgre;


import org.ifinal.finalframework.json.Json;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public class PGJsonListTypeHandler<T> extends PGJsonCollectionTypeHandler<T, List<T>> {
    public PGJsonListTypeHandler(Class<T> type) {
        super(type);
    }

    @Override
    protected List<T> getResult(String value, Class type) {
        return Json.toList(value, type);
    }
}
