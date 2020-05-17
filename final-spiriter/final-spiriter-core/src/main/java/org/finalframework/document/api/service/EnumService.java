package org.finalframework.document.api.service;

import java.util.List;

import org.finalframework.document.api.service.query.EnumQuery;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-10 22:01:44
 * @since 1.0
 */
public interface EnumService {
    List<Class<?>> query(EnumQuery query);
}
