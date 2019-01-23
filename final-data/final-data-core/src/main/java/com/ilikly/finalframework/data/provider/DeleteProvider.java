package com.ilikly.finalframework.data.provider;

import com.ilikly.finalframework.data.mapping.Entity;
import com.ilikly.finalframework.data.query.Query;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-18 22:06
 * @since 1.0
 */
public interface DeleteProvider<T> extends Provider<String> {

    DeleteProvider<T> DELETE(Entity<T> entity);

    DeleteProvider<T> QUERY(Query query);

}
