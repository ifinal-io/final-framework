package org.finalframework.data.query;

import org.finalframework.core.Streamable;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.ICriterion;
import org.finalframework.data.query.enums.AndOr;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:34
 * @since 1.0
 */
public interface Criteria extends ICriterion, Streamable<ICriterion>, Iterable<ICriterion> {

    static Criteria where(ICriterion... criterion) {
        return and(criterion);
    }

    static Criteria where(Collection<ICriterion> criterion) {
        return and(criterion);
    }

    static Criteria and(ICriterion... criterion) {
        return and(Arrays.asList(criterion));
    }

    static Criteria and(Collection<ICriterion> criterion) {
        return new CriteriaImpl(AndOr.AND, criterion);
    }

    static Criteria or(ICriterion... criterion) {
        return or(Arrays.asList(criterion));
    }

    static Criteria or(Collection<ICriterion> criterion) {
        return new CriteriaImpl(AndOr.OR, criterion);
    }

    AndOr andOr();

    @Override
    default boolean isChain() {
        return true;
    }

    default Criteria add(ICriterion... criterion) {
        return add(Arrays.asList(criterion));
    }

    Criteria add(Collection<ICriterion> criterion);

    Criteria and(Criteria... criteria);

    Criteria or(Criteria... criteria);

}
