package org.finalframework.data.entity;


import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:06
 * @since 1.0
 */
public interface IEntity<ID extends Serializable> extends Serializable {

    ID getId();

}
