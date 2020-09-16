package org.finalframework.data.query.criterion;


/**
 * @author likly
 * @version 1.0
 * @date 2020-06-01 15:12:42
 * @since 1.0
 */
public class CriterionTargetImpl<T> implements CriterionTarget<T> {
    private final T target;

    public CriterionTargetImpl(T target) {
        this.target = target;
    }

    public T getTarget() {
        return target;
    }
}

