package org.finalframework.data.query;

import org.finalframework.core.Assert;
import org.finalframework.core.converter.Convertible;
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
    private final List<Criteria> criteria = new ArrayList<>();
    private final Collection<Criterion> criterion = new ArrayList<>();

    protected AbsCriteria() {
        this(AndOr.AND, Collections.emptyList());
    }

    protected AbsCriteria(AndOr andOr) {
        this(andOr, Collections.emptyList());
    }

    protected AbsCriteria(AndOr andOr, Collection<Criterion> criterion) {
        this(andOr, Collections.emptyList(), criterion);
    }

    protected AbsCriteria(AndOr andOr, Collection<Criteria> criteria, Collection<Criterion> criterion) {
        this.andOr = andOr;
        if (Assert.nonEmpty(criteria)) {
            this.criteria.addAll(criteria);
        }
        if (Assert.nonEmpty(criterion)) {
            this.criterion.addAll(criterion);
        }
    }

    private boolean chain() {
        return !criteria.isEmpty();
    }

    protected T add(Criterion... criterion) {
        return add(Arrays.asList(criterion));
    }

    protected T add(Collection<Criterion> criterion) {
        if (chain()) {
            return and(andOr(AndOr.AND, Collections.emptyList(), criterion));
        } else {
            this.criterion.addAll(criterion);
            return (T) this;
        }
    }

    public T and(T... criteria) {
        return andOr(AndOr.AND, Arrays.stream(criteria).map(T::convert).collect(Collectors.toList()), Collections.emptyList());
    }


    public T or(T... criteria) {
        return andOr(AndOr.OR, Arrays.stream(criteria).map(T::convert).collect(Collectors.toList()), Collections.emptyList());
    }

    protected abstract T andOr(AndOr andOr, Collection<Criteria> criteria, Collection<Criterion> criterion);


    @Override
    public Criteria convert() {
        switch (andOr) {
            case AND:
                return chain() ? Criteria.where(this.criteria) : Criteria.and(this.criterion);
            case OR:
                return chain() ? Criteria.where(AndOr.OR, this.criteria) : Criteria.or(this.criterion);
        }

        throw new IllegalStateException();
    }
}
