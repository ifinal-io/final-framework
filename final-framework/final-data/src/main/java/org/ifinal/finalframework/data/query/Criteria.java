package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.data.query.criterion.Criterion;
import org.ifinal.finalframework.query.annotation.AndOr;
import org.springframework.lang.NonNull;

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

    static Criteria or(Criterion... criterion) {
        return or(Arrays.asList(criterion));
    }

    static Criteria or(Collection<Criterion> criterion) {
        return new CriteriaImpl(AndOr.OR, criterion);
    }

    AndOr andOr();

    default Criteria add(Criterion... criterion) {
        return add(Arrays.asList(criterion));
    }

    Criteria add(Collection<Criterion> criterion);

    Criteria and(Criteria... criteria);

    Criteria or(Criteria... criteria);


    @Override
    default void apply(@NonNull StringBuilder sql, @NonNull String value) {
        sql.append("<trim prefix=\"(\" prefixOverrides=\"AND |OR \" suffix=\")\">");
        int index = 0;

        for (Criterion criterion : this) {
            sql.append(String.format("<trim prefix=\" %s \">", andOr().name()));
            criterion.apply(sql, String.format("%s.criteria[%d]", value, index));
            sql.append("</trim>");
            index++;
        }

        sql.append("</trim>");


    }
}
