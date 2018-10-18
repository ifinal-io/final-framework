package cn.com.likly.finalframework.data.mapping.holder;

import cn.com.likly.finalframework.data.annotation.enums.PrimaryKeyType;
import cn.com.likly.finalframework.util.Assert;
import org.springframework.data.mapping.PersistentEntity;

import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 10:52
 * @since 1.0
 */
public interface EntityHolder<T, P extends PropertyHolder<P>> extends PersistentEntity<T, P>, Iterable<P> {


    static <T> EntityHolder<T, ? extends PropertyHolder> from(Class<T> entityClass) {
        Assert.isNull(entityClass, "entityClass must not be null!");
        return new BaseEntityHolder<>(entityClass);
    }

    PrimaryKeyType getPrimaryKeyType();

    String getTable();

    Stream<P> stream();


}
