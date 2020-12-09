package org.ifinal.finalframework.core.annotation;

import org.ifinal.finalframework.data.annotation.Transient;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * A marker superinterface of a entity must be impls.
 *
 * @author likly
 * @version 1.0.0
 * @see IEnum
 * @since 1.0.0
 */
@Transient
public interface IEntity<I extends Serializable> extends Serializable {

    /**
     * return the entity id.
     *
     * @return the entity id.
     */
    @Nullable
    I getId();

    /**
     * set id to this entity.
     *
     * @param id id
     */
    void setId(@Nullable I id);
}
