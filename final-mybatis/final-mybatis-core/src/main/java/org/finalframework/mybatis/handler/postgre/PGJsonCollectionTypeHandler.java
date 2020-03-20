package org.finalframework.mybatis.handler.postgre;


import java.util.Collection;
import org.finalframework.json.Json;

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
