package org.finalframework.data.query.condition;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-16 17:47:00
 * @since 1.0
 */
public interface JsonFunctionCondition<V, R> extends FunctionCondition {
    R extract(String path);
}
