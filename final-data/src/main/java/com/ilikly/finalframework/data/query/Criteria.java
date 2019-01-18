package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.core.Streable;
import com.ilikly.finalframework.data.query.enums.AndOr;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:34
 * @since 1.0
 */
public interface Criteria extends Streable<Criteria>, Iterable<Criteria> {

    static Criteria where(Criterion... criterion) {
        return where(Arrays.asList(criterion));
    }

    static Criteria where(Collection<Criterion> criterion) {
        return CriteriaImpl.where(criterion);
    }


    AndOr andOr();

    boolean chain();

    Stream<Criterion> criterionStream();

    Criteria and(Criteria... criteria);

    Criteria or(Criteria... criteria);

    interface Builder extends com.ilikly.finalframework.core.Builder<Criteria> {

    }


}
