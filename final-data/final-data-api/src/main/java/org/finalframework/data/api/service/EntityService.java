package org.finalframework.data.api.service;

import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.api.service.query.EntityQuery;
import org.finalframework.data.mapping.Entity;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 21:13:36
 * @since 1.0
 */
public interface EntityService {
    /**
     * return the {@link Class} which is a {@link IEntity}
     *
     * @param query the entity query
     * @return the {@link Class} which is a {@link IEntity}
     */
    List<Class<?>> query(EntityQuery query);

    Entity<?> entity(Class<?> entity);
}
