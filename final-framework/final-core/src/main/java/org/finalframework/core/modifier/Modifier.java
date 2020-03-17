package org.finalframework.core.modifier;

/**
 * 数据修改器，对现有数据进行修改以达到满足业务需求的目的
 *
 * @author likly
 * @version 1.0
 * @date 2019-12-13 09:44:28
 * @since 1.0
 */
@FunctionalInterface
public interface Modifier<T> {

    void modify(T data);
}
