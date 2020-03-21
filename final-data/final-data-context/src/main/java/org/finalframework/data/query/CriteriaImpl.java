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
    public Stream<Criterion> stream() {
        return criteria.stream();
    }

    @Override
    public Iterator<Criterion> iterator() {
        return criteria.iterator();
    }

    @Override
    public String getSql() {
        return new CriteriaSqlBuilder(this).build();
    }

}
