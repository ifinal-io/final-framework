package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.data.query.enums.AndOr;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 16:03:52
 * @since 1.0
 */
public class CriteriaImpl implements Criteria {
    private final AndOr andOr;
    private final List<Criteria> criteriaChain;
    private final List<Criterion> criterion;

    private CriteriaImpl(BuilderImpl builder) {
        this.andOr = builder.andOr;
        this.criteriaChain = builder.criteriaChain;
        this.criterion = builder.criterion;
    }

    static Criteria where(Collection<Criterion> criterion) {
        return new CriteriaImpl.BuilderImpl(AndOr.AND, criterion, Collections.emptyList()).build();
    }

    @Override
    public AndOr andOr() {
        return andOr;
    }

    @Override
    public boolean chain() {
        return !criteriaChain.isEmpty();
    }

    @Override
    public Stream<Criterion> criterionStream() {
        return criterion.stream();
    }

    @Override
    public Criteria and(Criteria... criteria) {
        List<Criteria> list = new ArrayList<>();
        list.add(this);
        list.addAll(Arrays.asList(criteria));
        return new BuilderImpl(AndOr.AND, Collections.emptyList(), list).build();
    }


    @Override
    public Criteria or(Criteria... criteria) {
        List<Criteria> list = new ArrayList<>();
        list.add(this);
        list.addAll(Arrays.asList(criteria));
        return new BuilderImpl(AndOr.OR, Collections.emptyList(), list).build();
    }

    @Override
    public Stream<Criteria> stream() {
        return criteriaChain.stream();
    }

    @NotNull
    @Override
    public Iterator<Criteria> iterator() {
        return criteriaChain.iterator();
    }

    private static class BuilderImpl implements Builder {
        private final AndOr andOr;
        private final List<Criterion> criterion = new ArrayList<>();
        private final List<Criteria> criteriaChain = new ArrayList<>();

        public BuilderImpl(AndOr andOr, Collection<Criterion> criterion, Collection<Criteria> criteriaChain) {
            this.andOr = andOr;
            this.criterion.addAll(criterion);
            this.criteriaChain.addAll(criteriaChain);
        }

        @Override
        public Criteria build() {
            return new CriteriaImpl(this);
        }
    }
}
