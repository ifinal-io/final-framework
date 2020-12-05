package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.annotation.query.AndOr;
import org.ifinal.finalframework.data.query.criterion.Criterion;

import java.util.*;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
final class CriteriaImpl implements Criteria {
    private final AndOr andOr;
    private final Collection<Criterion> criteria;

    public CriteriaImpl() {
        this(AndOr.AND, new ArrayList<>());
    }

    protected CriteriaImpl(AndOr andOr, Collection<Criterion> criterion) {
        this.andOr = andOr;
        this.criteria = criterion;
    }

    @Override
    public AndOr andOr() {
        return andOr;
    }

    public AndOr getAndOr() {
        return andOr;
    }

    @Override
    public Criteria add(Collection<Criterion> criterion) {
        this.criteria.addAll(criterion);
        return this;
    }

    @Override
    public Criteria and(Criteria... criteria) {
        return andOr(AndOr.AND, criteria);
    }


    @Override
    public Criteria or(Criteria... criteria) {
        return andOr(AndOr.OR, criteria);
    }

    private Criteria andOr(AndOr andOr, Criteria... criteria) {
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
