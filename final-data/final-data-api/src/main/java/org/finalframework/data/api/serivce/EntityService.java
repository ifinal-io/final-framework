package org.finalframework.data.api.serivce;

import org.finalframework.data.api.serivce.query.EntityQuery;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 21:13:36
 * @since 1.0
 */
public interface EntityService {
    List<Class<?>> query(EntityQuery query);
}
