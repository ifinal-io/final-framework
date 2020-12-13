package org.ifinal.finalframework.data.query;

import java.util.Arrays;
import java.util.Collection;
import org.ifinal.finalframework.annotation.query.AndOr;
import org.ifinal.finalframework.data.query.criterion.Criterion;
import org.springframework.lang.NonNull;

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

    Criteria and(Criteria... criteria);

    static Criteria and(Collection<Criterion> criterion) {
        return new CriteriaImpl(AndOr.AND, criterion);
    }

    default Criteria add(Criterion... criterion) {
        return add(Arrays.asList(criterion));
    }

    Criteria add(Collection<Criterion> criterion);

    static Criteria or(Criterion... criterion) {
        return or(Arrays.asList(criterion));
    }

    static Criteria or(Collection<Criterion> criterion) {
        return new CriteriaImpl(AndOr.OR, criterion);
    }

    Criteria or(Criteria... criteria);

    AndOr andOr();

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
