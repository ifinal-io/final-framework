package org.finalframework.annotation;

import org.finalframework.annotation.data.Creator;
import org.finalframework.annotation.data.LastModifier;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * The interface of {@code user} which impl the interface of {@link IEntity} in the system.
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-13 12:59:00
 * @see Creator
 * @see LastModifier
 * @since 1.0
 */
public interface IUser<ID extends Serializable> extends IEntity<ID> {
    /**
     * return user name.
     *
     * @return user name.
     */
    @Nullable
    String getName();

    /**
     * set user name.
     *
     * @param name user name.
     */
    void setName(@Nullable String name);
}
