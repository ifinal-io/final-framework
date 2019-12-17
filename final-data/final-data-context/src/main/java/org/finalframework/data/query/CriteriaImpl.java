package org.finalframework.data.query;

import org.finalframework.data.query.builder.CriteriaSqlBuilder;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.enums.AndOr;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 16:03:52
 * @since 1.0
 */
public class CriteriaImpl implements Criteria, Sql<Criteria> {
    private final AndOr andOr;
    private final Collection<Criteria> criteria;
    private final Collection<Criterion> criterion;

    public CriteriaImpl() {
        this(AndOr.AND, new ArrayList<>());
    }

    protected CriteriaImpl(AndOr andOr, Collection<Criterion> criterion) {
        this(andOr, Collections.EMPTY_LIST, criterion);
    }

    protected CriteriaImpl(AndOr andOr, Collection<Criteria> criteria, Collection<Criterion> criterion) {
        this.andOr = andOr;
        this.criteria = criteria;
        this.criterion = criterion;
    }

    @Override
    public AndOr andOr() {
        return andOr;
    }

    @Override
    public boolean chain() {
        return !criteria.isEmpty();
    }

    public boolean getChain() {
        return chain();
    }


    public AndOr getAndOr() {
        return andOr;
    }

    public Collection<Criteria> getCriteria() {
        return criteria;
    }

    public Collection<Criterion> getCriterion() {
        return criterion;
    }

    @Override
    public Stream<Criterion> criterionStream() {
        return criterion.stream();
    }

    @Override
    public Criteria add(Collection<Criterion> criterion) {
        if (chain()) {
            return and(Criteria.where(criterion));
        } else {
            this.criterion.addAll(criterion);
            return this;
        }
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
        List<Criteria> list = new ArrayList<>();
        list.add(this);
        list.addAll(Arrays.asList(criteria));
        return new CriteriaImpl(andOr, list, Collections.emptyList());
    }

    @Override
    public Stream<Criteria> stream() {
        return criteria.stream();
    }

    @Override
    public Iterator<Criteria> iterator() {
        return criteria.iterator();
    }

    @Override
    public String getSql() {
        return new CriteriaSqlBuilder(this).build();
    }

}
