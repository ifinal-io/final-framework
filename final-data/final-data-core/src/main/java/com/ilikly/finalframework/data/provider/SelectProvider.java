package com.ilikly.finalframework.data.provider;

import com.ilikly.finalframework.data.mapping.Entity;
import com.ilikly.finalframework.data.query.Query;
import lombok.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 11:04
 * @since 1.0
 */
public interface SelectProvider<T> extends Provider<String> {

    SelectProvider<T> SELECT(@NonNull Entity<T> holder);

    SelectProvider<T> SELECT_COUNT(@NonNull Entity<T> holder);

    SelectProvider<T> QUERY(Query query);
}
