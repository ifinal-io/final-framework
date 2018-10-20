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
public interface EntityHolder<T> extends PersistentEntity<T, PropertyHolder>, Iterable<PropertyHolder> {

    static <T> EntityHolder<T> from(Class<T> entityClass) {
        Assert.isNull(entityClass, "entityClass must not be null!");
        return new BaseEntityHolder<>(entityClass);
    }

    default T getInstance() {
        try {
            return getType().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(String.format("the entity of %s must have no args constructor!", getType().getName()));
        }
    }

    PrimaryKeyType getPrimaryKeyType();

    String getTable();

    PropertyHolder getPropertyByColumn(String column);

    Stream<PropertyHolder> stream();


}
