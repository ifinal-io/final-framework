package cn.com.likly.finalframework.data.mapping;

import cn.com.likly.finalframework.data.annotation.enums.PrimaryKeyType;
import cn.com.likly.finalframework.data.domain.BaseEntity;
import cn.com.likly.finalframework.util.Assert;
import cn.com.likly.finalframework.util.Streable;
import org.springframework.data.mapping.PersistentEntity;

/**
 * @author likly
 * @version 1.0PP
 * @date 2018-10-17 10:52
 * @since 1.0
 */
public interface Entity<T> extends PersistentEntity<T, Property>, Streable<Property>, Iterable<Property> {

    static <T> Entity<T> from(Class<T> entityClass) {
        Assert.isNull(entityClass, "entityClass must not be null!");
        return new BaseEntity<>(entityClass);
    }

    String getTable();

    PrimaryKeyType getPrimaryKeyType();

    default T getInstance() {
        try {
            return getType().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(String.format("the entity of %s must have no args constructor!", getType().getName()));
        }
    }

}
