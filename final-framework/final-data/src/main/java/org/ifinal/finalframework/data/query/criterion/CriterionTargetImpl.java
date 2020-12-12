package org.ifinal.finalframework.data.query.criterion;


/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class CriterionTargetImpl<T> implements CriterionTarget<T> {
    private final T target;

    public CriterionTargetImpl(final T target) {

        this.target = target;
    }

    public T getTarget() {
        return target;
    }
}

