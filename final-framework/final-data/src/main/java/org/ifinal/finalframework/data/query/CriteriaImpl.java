package org.ifinal.finalframework.data.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.ifinal.finalframework.annotation.query.AndOr;
import org.ifinal.finalframework.data.query.criterion.Criterion;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
final class CriteriaImpl implements Criteria {

    private final AndOr andOr;

    private final Collection<Criterion> criteria;

    CriteriaImpl() {
        this(AndOr.AND, new ArrayList<>());
    }

    protected CriteriaImpl(final AndOr andOr, final Collection<Criterion> criterion) {

        this.andOr = andOr;
        this.criteria = criterion;
    }

    public AndOr getAndOr() {
        return andOr;
    }

    @Override
    public Criteria add(final Collection<Criterion> criterion) {

        this.criteria.addAll(criterion);
        return this;
    }

    @Override
    public Criteria and(final Criteria... criteria) {

        return andOr(AndOr.AND, criteria);
    }

    @Override
    public Criteria or(final Criteria... criteria) {

        return andOr(AndOr.OR, criteria);
    }

    @Override
    public AndOr andOr() {
        return andOr;
    }

    private Criteria andOr(final AndOr andOr, final Criteria... criteria) {

        List<Criterion> list = new ArrayList<>();
        list.add(this);
        list.addAll(Arrays.asList(criteria));
        return new CriteriaImpl(andOr, list);
    }

    @Override
    public Iterator<Criterion> iterator() {
        return criteria.iterator();
    }

}
