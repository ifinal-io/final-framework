package org.finalframework.data.query.condition;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 13:20:30
 * @since 1.0
 */
public interface DateFunctionCondition<R> extends FunctionCondition {
    R date();
}
