package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.core.Streable;
import cn.com.likly.finalframework.data.domain.enums.AndOr;
import cn.com.likly.finalframework.data.mapping.Property;
import lombok.Getter;

import java.util.*;
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
        this.criteriaSets = Arrays.asList(criteriaSet);
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

    public static Criteriable<Criteria> where(Property property) {
        return CriteriaSet.where(property);
    }

    public Criteriable<Criteria> and(Property property) {
        return new CriteriaSet(this, property, AndOr.AND);
    }

    public Criteriable<Criteria> or(Property property) {
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
