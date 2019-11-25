package org.finalframework.data.query.criterion;

import org.finalframework.data.query.Criterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-25 14:28:03
 * @since 1.0
 */
public interface LikeCriterion<T> extends Criterion<T> {

    static <T> Builder<T> builder() {
        return LikeCriterionImpl.builder();
    }

    String getPrefix();

    T getValue();

    String getSuffix();

    interface Builder<T> extends Criterion.Builder<LikeCriterion<T>, Builder<T>> {

        Builder<T> like(String prefix, T value, String suffix);
    }
}
