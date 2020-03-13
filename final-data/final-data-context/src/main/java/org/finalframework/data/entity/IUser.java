package org.finalframework.data.entity;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-13 12:59:00
 * @since 1.0
 */
public interface IUser<ID extends Serializable> extends IEntity<ID> {
    String getName();

    void setName(String name);
}
