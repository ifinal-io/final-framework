package org.ifinal.finalframework.data.query.condition;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface NullCondition<R> extends Condition {

    R isNull();

    R isNotNull();
}
