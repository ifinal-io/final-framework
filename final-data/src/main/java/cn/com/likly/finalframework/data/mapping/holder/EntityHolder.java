package cn.com.likly.finalframework.data.mapping.holder;

import cn.com.likly.finalframework.data.annotation.enums.PrimaryKeyType;
import org.springframework.data.mapping.PersistentEntity;

import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 10:52
 * @since 1.0
 */
public interface EntityHolder<T, P extends PropertyHolder<P>> extends PersistentEntity<T, P>, Iterable<P> {


    static EntityHolder<?, ? extends PropertyHolder> from(Class<?> entityClass) {
        return new BaseEntityHolder<>(entityClass);
    }

    PrimaryKeyType getPrimaryKeyType();

    String getTable();

    Stream<P> stream();


}
