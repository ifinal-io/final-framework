package org.finalframework.data.entity;

import org.finalframework.data.annotation.IUser;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-13 13:10:25
 * @since 1.0
 */
public interface IRecord<ID extends Serializable, USER extends IUser<ID>> {

    USER getCreator();

    void setCreator(USER creator);

    USER getLastModifier();

    void setLastModifier(USER modifier);

}
