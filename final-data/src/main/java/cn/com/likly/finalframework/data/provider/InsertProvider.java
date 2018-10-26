package cn.com.likly.finalframework.data.provider;

import cn.com.likly.finalframework.data.mapping.Entity;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-18 22:00
 * @since 1.0
 */
public interface InsertProvider<T> extends Provider<String> {

    InsertProvider<T> INSERT_INTO(@NonNull Entity<T> entity);

    default InsertProvider<T> INSERT_VALUES(@NonNull T... entities) {
        return INSERT_VALUES(Arrays.asList(entities));
    }

    InsertProvider<T> INSERT_VALUES(@NonNull Collection<T> entities);

}
