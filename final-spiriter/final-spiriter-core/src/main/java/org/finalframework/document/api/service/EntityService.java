package org.finalframework.document.api.service;

import java.util.List;

import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.mapping.Entity;
import org.finalframework.document.api.service.query.EntityQuery;

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

    /**
     * return the {@link Entity} of the {@code entity}.
     *
     * @param entity the {@link Class<?>} of entity
     */
    Entity<?> entity(Class<?> entity);
}
