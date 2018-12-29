package com.ilikly.finalframework.data.mapping;

import com.ilikly.finalframework.core.Assert;
import com.ilikly.finalframework.core.Streable;
import com.ilikly.finalframework.data.annotation.enums.PrimaryKeyType;
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
        return EntityCache.getInstance().get(entityClass);
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
