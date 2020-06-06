package org.finalframework.data.query.criterion;

import org.finalframework.core.Builder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-06 20:34:28
 * @since 1.0
 */
public interface CompareCriterionOperation extends CriterionOperation {

    static CompareCriterionOperationBuilder builder() {
        return AbsCompareCriterionOperation.builder();
    }

    @NonNull
    Object getTarget();

    @Override
    CompareOperation getOperation();

    @Nullable
    Object getValue();

    @Nullable
    Object getMin();

    @Nullable
    Object getMax();


    interface CompareCriterionOperationBuilder extends Builder<CompareCriterionOperation> {

        CompareCriterionOperationBuilder target(Object target);

        CompareCriterionOperationBuilder operation(CompareOperation operation);

        CompareCriterionOperationBuilder value(Object value);

        CompareCriterionOperationBuilder min(Object min);

        CompareCriterionOperationBuilder max(Object max);

    }


}
