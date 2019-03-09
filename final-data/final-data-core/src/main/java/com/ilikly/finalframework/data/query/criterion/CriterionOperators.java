package com.ilikly.finalframework.data.query.criterion;

import com.ilikly.finalframework.data.query.CriterionOperation;
import com.ilikly.finalframework.data.query.CriterionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-20 09:31:57
 * @since 1.0
 */
public interface CriterionOperators<T> {
    Class<T> type();

    CriterionOperation get(CriterionOperator operator);

    void register(CriterionOperation operation);

}
