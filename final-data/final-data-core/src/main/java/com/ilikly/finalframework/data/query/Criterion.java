package com.ilikly.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 12:19:55
 * @since 1.0
 * @see CriterionOperation
 */
public interface Criterion<T> {

    QProperty property();

    CriterionOperator operator();


    interface Builder<T> extends com.ilikly.finalframework.core.Builder<Criterion> {
        Builder<T> property(QProperty property);

        Builder<T> operator(CriterionOperator operation);

    }

}
