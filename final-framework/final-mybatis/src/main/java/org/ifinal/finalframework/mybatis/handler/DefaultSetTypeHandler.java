package org.ifinal.finalframework.mybatis.handler;

import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultSetTypeHandler<E> extends DefaultCollectionTypeHandler<E, Set<E>> {
    public DefaultSetTypeHandler(Class<E> type) {
        super(type);
    }

    @Override
    protected Set<E> getCollection() {
        return new HashSet<>();
    }
}
