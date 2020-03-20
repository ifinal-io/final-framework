package org.finalframework.data.query.criterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:34:21
 * @since 1.0
 */
public interface CriterionOperation<T, C extends Criterion> {

    CriterionOperator operator();

    String format(C criterion);

}
