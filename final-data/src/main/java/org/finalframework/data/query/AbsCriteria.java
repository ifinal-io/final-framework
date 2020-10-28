package org.finalframework.data.query;

import org.finalframework.annotation.query.AndOr;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.util.function.Convertible;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 16:03:52
 * @since 1.0
 */
@Deprecated
public abstract class AbsCriteria<T extends AbsCriteria> implements Convertible<Criteria> {
    private final AndOr andOr;
    private final List<Criterion> criteria = new ArrayList<>();

    protected AbsCriteria() {
        this(AndOr.AND, Collections.emptyList());
    }

    protected AbsCriteria(AndOr andOr) {
        this(andOr, Collections.emptyList());
    }

    protected AbsCriteria(AndOr andOr, Collection<? extends Criterion> criteria) {
        this.andOr = andOr;
        this.criteria.addAll(criteria);
    }

    private boolean chain() {
        return !criteria.isEmpty();
    }

    public T add(Criterion... criterion) {
        return add(Arrays.asList(criterion));
    }

    public T add(Collection<Criterion> criterion) {
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
