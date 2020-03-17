package org.finalframework.core.validator;


/**
 * @author likly
 * @version 1.0
 * @date 2020-03-17 22:59
 * @since 1.0
 */
public interface Validator<R, T> {

    /**
     * validate the data is right.
     *
     * @param data the data to validate
     * @return the validate result
     * @throws Throwable throw a {@link Throwable} when the data must pass this validator, but it's not.
     */
    R validate(T data) throws Throwable;
}
