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
public interface PropertyHolder<P extends PersistentProperty<P>> extends PersistentProperty<P> {

    @NonNull
    String getTable();

    @NonNull
    String getColumn();

    PersistentType getPersistentType();

    boolean unique();

    boolean nullable();

    boolean insertable();

    boolean updatable();

}
