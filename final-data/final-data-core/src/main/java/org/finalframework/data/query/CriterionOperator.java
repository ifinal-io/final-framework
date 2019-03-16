package org.finalframework.data.query;

import org.finalframework.data.query.criterion.CriterionOperators;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-20 09:30:13
 * @since 1.0
 * @see CriterionOperators
 */
public interface CriterionOperator {
    @NonNull
    String name();


}
