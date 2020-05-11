package org.finalframework.data.api.serivce;

import org.finalframework.data.api.serivce.query.EnumQuery;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-10 22:01:44
 * @since 1.0
 */
public interface EnumService {
    List<Class<?>> query(EnumQuery query);
}
