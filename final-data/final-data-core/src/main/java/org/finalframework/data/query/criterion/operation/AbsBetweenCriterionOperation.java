package org.finalframework.data.query.criterion.operation;

import org.finalframework.data.query.criterion.DoubleCriterion;
import org.finalframework.data.query.criterion.DoubleCriterionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class AbsBetweenCriterionOperation<T> extends AbsCriterionOperation<T> implements DoubleCriterionOperation<T> {
    @Override
    public final String format(DoubleCriterion<T> criterion) {
        return format(criterion.property(), criterion.functions(), criterion.operator(), criterion.min(), criterion.max());
    }
}
