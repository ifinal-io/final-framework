package org.ifinal.finalframework.data.query.criterion.function;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface MathFunction<R> {

    R min();

    R max();

    R sum();

    R avg();

}
