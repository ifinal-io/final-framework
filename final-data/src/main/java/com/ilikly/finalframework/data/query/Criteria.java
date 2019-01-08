package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.core.Streable;
import com.ilikly.finalframework.data.query.enums.AndOr;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:34
 * @since 1.0
 */
public class Criteria implements Streable<Criteria>, Iterable<Criteria> {

    private final List<Criteria> criteriaChain;
    private final List<CriteriaSet> criteriaSets;
    @Getter
    private final boolean chain;

    Criteria(CriteriaSet criteriaSet) {
        this.criteriaChain = null;
        this.criteriaSets = new ArrayList<>();
        this.criteriaSets.add(criteriaSet);
        this.chain = false;
    }

    public Criteria() {
        this(new ArrayList<>());
    }

    Criteria(Collection<Criteria> criteria) {
        this.criteriaChain = new ArrayList<>(criteria);
        this.criteriaSets = null;
        this.chain = true;
    }

    public static Criteriable<Criteria> where(QProperty property) {
        return CriteriaSet.where(property);
    }

    public Criteriable<Criteria> and(QProperty property) {
        return new CriteriaSet(this, property, AndOr.AND);
    }

    public Criteriable<Criteria> or(QProperty property) {
        return new CriteriaSet(this, property, AndOr.OR);
    }

    Criteria addCriteriaSet(CriteriaSet criteriaSet) {
        if (chain) {
            this.criteriaChain.add(new Criteria(criteriaSet));
        } else {
            this.criteriaSets.add(criteriaSet);
        }
        return this;
    }

    @Override
    public Stream<Criteria> stream() {
        return criteriaChain.stream();
    }

    @Override
    public Iterator<Criteria> iterator() {
        return criteriaChain.iterator();
    }

    public Stream<CriteriaSet> setStream() {
        return criteriaSets.stream();
    }


}
