package cn.com.likly.finalframework.data.provider;

import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.mapping.Entity;
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
