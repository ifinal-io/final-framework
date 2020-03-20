package org.finalframework.data.query.condition;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 00:18:17
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface NullCondition<R> extends Condition {

    R isNull();

    R isNotNull();
}
