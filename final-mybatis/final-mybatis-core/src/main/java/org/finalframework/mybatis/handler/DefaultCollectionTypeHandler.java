package org.finalframework.mybatis.handler;

import org.finalframework.core.PrimaryTypes;

import java.util.AbstractCollection;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 10:22
 * @since 1.0
 */
public abstract class DefaultCollectionTypeHandler<E, T extends Collection<E>> extends CollectionTypeHandler<E, T> {

    public DefaultCollectionTypeHandler(Class<E> type) {
        super(type);
//        if (!JavaType.isPrimary(type)) {
        //            throw new IllegalArgumentException("the target type eq not support " + type.getName());
//        }
    }

    @Override
    protected String setParameter(T parameter) {

        if (parameter == null || parameter.isEmpty()) return null;

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (E item : parameter) {
            if (i++ != 0) {
                sb.append(",");
            }
            sb.append(item.toString());
        }

        return sb.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected final T getResult(String value, Class<E> type) {

        if (value == null || value.trim().isEmpty()) return null;

        AbstractCollection result = (AbstractCollection) getCollection();

        for (String item : value.split(",")) {
            if (PrimaryTypes.isString(type)) {
                result.add(item);
            } else if (PrimaryTypes.isInteger(type)) {
                result.add(Integer.parseInt(item));
            } else if (PrimaryTypes.isLong(type)) {
                result.add(Long.parseLong(item));
            } else if (PrimaryTypes.isBoolean(type)) {
                result.add(Boolean.parseBoolean(item));
            } else {
                throw new IllegalArgumentException("the target type eq not support " + type.getName());
            }
        }

        return (T) result;
    }

    protected abstract T getCollection();

}
