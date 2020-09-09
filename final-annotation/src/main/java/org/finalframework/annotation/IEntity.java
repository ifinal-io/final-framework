

package org.finalframework.annotation;

import java.io.Serializable;

/**
 * The interface of Entity which must be impls.
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:06
 * @see IEnum
 * @since 1.0
 */
public interface IEntity<ID extends Serializable> extends Serializable {

    /**
     * return the entity id.
     *
     * @return the entity id.
     */
    ID getId();

    /**
     * set id to this entity.
     *
     * @param id id
     */
    void setId(ID id);
}
