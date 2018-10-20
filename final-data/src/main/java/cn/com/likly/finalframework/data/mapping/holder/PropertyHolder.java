package cn.com.likly.finalframework.data.mapping.holder;

import cn.com.likly.finalframework.data.annotation.enums.PersistentType;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.lang.NonNull;


/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 10:52
 * @since 1.0
 */
public interface PropertyHolder extends PersistentProperty<PropertyHolder> {

    @NonNull
    String getTable();

    @NonNull
    String getColumn();

    PersistentType getPersistentType();

    boolean unique();

    boolean nullable();

    boolean insertable();

    boolean updatable();

    Object get(Object target);

    void set(Object target, Object value);

}
