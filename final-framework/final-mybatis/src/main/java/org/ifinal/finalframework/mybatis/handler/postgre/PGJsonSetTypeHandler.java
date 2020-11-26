package org.ifinal.finalframework.mybatis.handler.postgre;


import org.ifinal.finalframework.json.Json;

import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public class PGJsonSetTypeHandler<T> extends PGJsonCollectionTypeHandler<T, Set<T>> {


    public PGJsonSetTypeHandler(Class<T> type) {
        super(type);
    }

    @Override
    protected Set<T> getResult(String value, Class type) {
        return Json.toSet(value, type);
    }
}
