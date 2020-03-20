package org.finalframework.data.query.condition;

import org.finalframework.data.query.criterion.CriterionOperator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author sli
 * @version 1.0
 * @date 2020-03-20 20:43:22
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface JsonCondition<V, R> extends ExecutableCondition {

    /**
     * @see CriterionOperator#JSON_CONTAINS
     */
    default R jsonContains(@NonNull V value) {
        return jsonContains(value, null);
    }

    /**
     * @see CriterionOperator#JSON_CONTAINS
     */
    R jsonContains(@NonNull V value, @Nullable String path);

    /**
     * @see CriterionOperator#NOT_JSON_CONTAINS
     */
    default R notJsonContains(@NonNull V value) {
        return notJsonContains(value, null);
    }

    /**
     * @see CriterionOperator#NOT_JSON_CONTAINS
     */
    R notJsonContains(@NonNull V value, @Nullable String path);

}
