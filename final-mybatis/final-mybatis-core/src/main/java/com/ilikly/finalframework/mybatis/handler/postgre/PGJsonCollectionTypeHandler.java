package com.ilikly.finalframework.mybatis.handler.postgre;


import com.ilikly.finalframework.json.Json;
import com.ilikly.finalframework.mybatis.handler.CollectionTypeHandler;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:48
 * @since 1.0
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
