package org.finalframework.data.query.builder;


import org.finalframework.data.query.*;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.CriterionOperation;
import org.finalframework.data.query.criterion.CriterionOperationRegistry;
import org.finalframework.data.query.enums.AndOr;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-06 14:59:55
 * @since 1.0
 */
public class CriteriaSqlBuilder implements SqlBuilder<Criteria> {
    private static final CriterionOperationRegistry criterionOperationRegistry = CriterionOperationRegistry.getInstance();
    private final Criteria criteria;

    public CriteriaSqlBuilder(Criteria criteria) {
        this.criteria = criteria;
    }

    private static Class getPropertyJavaType(QProperty property) {
        return property.getType();
    }

    @Override
    public String build() {
        if (criteria.chain()) {
            return joinCriteria(criteria.stream().collect(Collectors.toList()), AndOr.AND);
        } else {
            return joinCriteriaSet(criteria.criterionStream().collect(Collectors.toList()), AndOr.AND);
        }
    }

    private String joinCriteria(Collection<Criteria> criteria, AndOr andOr) {
        final StringBuilder sb = new StringBuilder();
        if (criteria.size() > 1) {
            sb.append("(");
        }
        sb.append(
                criteria.stream().map(
                        it -> it.chain()
                                ? joinCriteria(it.stream().collect(Collectors.toList()), it.andOr())
                                : joinCriteriaSet(it.criterionStream().collect(Collectors.toList()), it.andOr()))
                        .collect(Collectors.joining(String.format(" %s ", andOr)))
        );

        if (criteria.size() > 1) {
            sb.append(")");
        }

        return sb.toString();
    }

    private String joinCriteriaSet(Collection<Criterion> criteriaSets, AndOr andOr) {
        final StringBuilder sb = new StringBuilder();

        if (criteriaSets.size() > 1) {
            sb.append("(");
        }

        sb.append(
                criteriaSets.stream()
                        .map(criterion -> {
                            final Class javaType = getPropertyJavaType(criterion.getProperty());
                            CriterionOperation criterionOperation = criterionOperationRegistry.getCriterionOperation(criterion.getOperator(), javaType);
                            return criterionOperation.format(criterion);
                        })
                        .collect(Collectors.joining(String.format(" %s ", andOr.name())))
        );


        if (criteriaSets.size() > 1) {
            sb.append(")");
        }
        return sb.toString();
    }


}
