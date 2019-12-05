package org.finalframework.data.query;

import org.finalframework.core.Streamable;
import org.finalframework.data.query.enums.AndOr;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:34
 * @since 1.0
 */
public interface Criteria extends Streamable<Criteria>, Iterable<Criteria> {

    static Criteria where(Criterion... criterion) {
        return where(Arrays.asList(criterion));
    }

    static Criteria where(Collection<Criterion> criterion) {
        return new CriteriaImpl(AndOr.AND, criterion);
    }

    static Criteria and(Criterion... criterion) {
        return and(Arrays.asList(criterion));
    }

    static Criteria and(Collection<Criterion> criterion) {
        return new CriteriaImpl(AndOr.AND, criterion);
    }

    static Criteria or(Criterion... criterion) {
        return or(Arrays.asList(criterion));
    }

    static Criteria or(Collection<Criterion> criterion) {
        return new CriteriaImpl(AndOr.OR, criterion);
    }

    static Criteria where(Criteria... criterion) {
        return where(Arrays.asList(criterion));
    }

    static Criteria where(List<Criteria> criteria) {
        return where(AndOr.AND, criteria);
    }

    static Criteria where(AndOr andOr, List<Criteria> criteria) {
        return new CriteriaImpl(andOr, criteria, Collections.emptyList());
    }

    AndOr andOr();

    boolean chain();

    Collection<Criteria> getCriteria();

    Collection<Criterion> getCriterion();

    Stream<Criterion> criterionStream();

    default Criteria add(Criterion... criterion) {
        return add(Arrays.asList(criterion));
    }

    Criteria add(Collection<Criterion> criterion);

    Criteria and(Criteria... criteria);

    Criteria or(Criteria... criteria);

}
