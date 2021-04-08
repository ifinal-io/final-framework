package org.ifinal.finalframework.data.query;

import org.springframework.lang.NonNull;

import org.ifinal.finalframework.query.AndOr;
import org.ifinal.finalframework.query.Criterion;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Criteria extends Criterion, Iterable<Criterion>, SqlNode {

    static Criteria where(Criterion... criterion) {
        return and(criterion);
    }

    static Criteria where(Collection<Criterion> criterion) {
        return and(criterion);
    }

    static Criteria and(Criterion... criterion) {
        return and(Arrays.asList(criterion));
    }

    static Criteria and(Collection<Criterion> criterion) {
        return new CriteriaImpl(AndOr.AND, criterion);
    }

    Criteria and(Criteria... criteria);

    static Criteria or(Criterion... criterion) {
        return or(Arrays.asList(criterion));
    }

    static Criteria or(Collection<Criterion> criterion) {
        return new CriteriaImpl(AndOr.OR, criterion);
    }

    Criteria or(Criteria... criteria);

    Criteria add(Collection<Criterion> criterion);

    default Criteria add(Criterion... criterion) {
        return add(Arrays.asList(criterion));
    }

    AndOr andOr();

    @Override
    default void apply(@NonNull StringBuilder sql, @NonNull String value) {
//        sql.append("<trim prefix=\"(\" prefixOverrides=\"AND |OR \" suffix=\")\">");
//        int index = 0;
//
//        for (Criterion criterion : this) {
//            sql.append(String.format("<trim prefix=\" %s \">", andOr().name()));
//            criterion.apply(sql, String.format("%s.criteria[%d]", value, index));
//            sql.append("</trim>");
//            index++;
//        }
//
//        sql.append("</trim>");

    }

}
