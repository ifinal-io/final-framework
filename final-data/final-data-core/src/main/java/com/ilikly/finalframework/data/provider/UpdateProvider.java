package com.ilikly.finalframework.data.provider;

import com.ilikly.finalframework.data.mapping.Entity;
import com.ilikly.finalframework.data.query.Query;
import com.ilikly.finalframework.data.query.Update;
import lombok.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-20 00:07
 * @since 1.0
 */
public interface UpdateProvider<T> extends Provider<String> {

    UpdateProvider<T> UPDATE(@NonNull Entity<T> holder);

    UpdateProvider<T> SET(Update update);

    UpdateProvider<T> ENTITY(T entity);

    UpdateProvider<T> QUERY(Query query);
}
