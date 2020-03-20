package org.finalframework.data.query.criterion.function;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-09 16:48:54
 * @since 1.0
 */
public interface MathFunction<V, R> extends Function {

    R min();

    R max();

    R sum();

    R avg();

}
