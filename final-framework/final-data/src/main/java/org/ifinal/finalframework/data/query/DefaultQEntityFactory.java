package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.query.QEntity;
import org.ifinal.finalframework.query.QEntityFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DefaultQEntityFactory.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultQEntityFactory implements QEntityFactory {

    public static final QEntityFactory INSTANCE = new DefaultQEntityFactory();

    private final Map<Class<?>, QEntity<Serializable, ?>> cache = new ConcurrentHashMap<>();

    @Override
    public QEntity<?, ?> create(final Class<?> entity) {
        return cache.computeIfAbsent(entity, clazz -> new AbsQEntity<>(entity));
    }

}
