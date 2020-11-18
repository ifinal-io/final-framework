package org.finalframework.annotation;

import org.finalframework.annotation.data.Transient;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * A marker superinterface of a entity must be impls.
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:06
 * @see IEnum
 * @since 1.0
 */
@Transient
public interface IEntity<ID extends Serializable> extends Serializable {

    /**
     * return the entity id.
     *
     * @return the entity id.
     */
    @Nullable
    ID getId();

    /**
     * set id to this entity.
     *
     * @param id id
     */
    void setId(@Nullable ID id);
}
