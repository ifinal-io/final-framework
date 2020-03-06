package org.finalframework.data.query;

import org.finalframework.core.converter.Convertible;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.ICriterion;
import org.finalframework.data.query.enums.AndOr;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 16:03:52
 * @since 1.0
 */
public abstract class AbsCriteria<T extends AbsCriteria> implements Convertible<Criteria> {
    private final AndOr andOr;
    private final List<ICriterion> criteria = new ArrayList<>();

    protected AbsCriteria() {
        this(AndOr.AND, Collections.emptyList());
    }

    protected AbsCriteria(AndOr andOr) {
        this(andOr, Collections.emptyList());
    }

    protected AbsCriteria(AndOr andOr, Collection<? extends ICriterion> criteria) {
        this.andOr = andOr;
        this.criteria.addAll(criteria);
    }

    private boolean chain() {
        return !criteria.isEmpty();
    }

    public T add(ICriterion... criterion) {
        return add(Arrays.asList(criterion));
    }

    public T add(Collection<ICriterion> criterion) {
        this.criteria.addAll(criterion);
        return (T) this;
    }

    public T and(T... criteria) {
        return andOr(AndOr.AND, Arrays.stream(criteria).map(T::convert).collect(Collectors.toList()));
    }


    public T or(T... criteria) {
        return andOr(AndOr.OR, Arrays.stream(criteria).map(T::convert).collect(Collectors.toList()));
    }

    protected abstract T andOr(AndOr andOr, Collection<Criteria> criteria);


    @Override
    public Criteria convert() {
        switch (andOr) {
            case AND:
                return Criteria.and(this.criteria);
            case OR:
                return Criteria.or(this.criteria);
        }

        throw new IllegalStateException();
    }
}
