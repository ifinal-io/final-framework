package org.finalframework.data.query.criterion.operator;

import org.finalframework.data.query.criterion.CriterionOperations;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-20 09:30:13
 * @since 1.0
 * @see CriterionOperations
 */
public interface CriterionOperator {
    @NonNull
    String name();

}
