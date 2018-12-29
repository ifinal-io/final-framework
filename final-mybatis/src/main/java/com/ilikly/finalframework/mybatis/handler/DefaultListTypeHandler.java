package com.ilikly.finalframework.mybatis.handler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 09:31
 * @since 1.0
 */
public class DefaultListTypeHandler<E> extends DefaultCollectionTypeHandler<E, List<E>> {
    public DefaultListTypeHandler(Class<E> type) {
        super(type);
    }

    @Override
    protected List<E> getCollection() {
        return new ArrayList<>();
    }
}
