package com.ilikly.finalframework.coding.element;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 09:57
 * @since 1.0
 */
public interface MutableEntity<P extends Property> extends Entity<P> {

    void addProperty(P property);

}
