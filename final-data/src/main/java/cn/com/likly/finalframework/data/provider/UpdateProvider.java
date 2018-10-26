package cn.com.likly.finalframework.data.provider;

import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.domain.Update;
import cn.com.likly.finalframework.data.mapping.Entity;
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
