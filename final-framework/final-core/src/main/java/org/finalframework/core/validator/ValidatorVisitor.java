package org.finalframework.core.validator;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-17 22:59
 * @since 1.0
 */
public interface ValidatorVisitor<R, T, P> {

    R validate(T data, P param);
}
