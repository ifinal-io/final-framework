package org.ifinal.finalframework.mybatis.handler.postgre;


import org.ifinal.finalframework.json.Json;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class PGJsonCollectionTypeHandler<E, T extends Collection<E>> extends PGCollectionTypeHandler<E, T> {

    public PGJsonCollectionTypeHandler(Class<E> type) {
        super(type);
    }

    @Override
    protected String setParameter(T parameter) {
        return Json.toJson(parameter);
    }
}
