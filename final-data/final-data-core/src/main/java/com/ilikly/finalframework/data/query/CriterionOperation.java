package com.ilikly.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:34:21
 * @since 1.0
 */
public interface CriterionOperation<T, C extends Criterion<T>> {

    CriterionOperator operator();

    String format(C criterion);

}
