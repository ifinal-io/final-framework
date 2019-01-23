package com.ilikly.finalframework.data.provider;

import com.ilikly.finalframework.data.query.*;
import com.ilikly.finalframework.data.query.enums.AndOr;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-22 19:58:40
 * @since 1.0
 */
public class QuerySqlBuilder implements SqlProvider<Query> {

    private final CriterionOperationRegistry criterionOperationRegistry = new CriterionOperationRegistry();
    private final Query query;

    public QuerySqlBuilder(Query query) {
        this.query = query;
    }

    private static Class getPropertyJavaType(QProperty property) {
        return property.isCollectionLike() ? property.getComponentType() : property.getType();
    }

    private String getSort(Sort sort) {
        return sort.stream()
                .map(it -> String.format("%s %s", getFormatProperty(it.property()), it.direction().name()))
                .collect(Collectors.joining(","));
    }


    private String getFormatProperty(QProperty property) {
        return String.format("%s.%s", property.getTable(), property.getColumn());
    }

    @Override
    public String provide() {
        if (query == null) return "";

        final StringBuilder sb = new StringBuilder();
        if (query.stream().count() > 0) {
            sb.append(joinCriteria(query.stream().collect(Collectors.toList()), AndOr.AND));
        }

        if (query.getSort() != null) {
            sb.append(" ORDER BY ").append(getSort(query.getSort()));
        }

        if (query.getLimit() != null) {
            sb.append(" LIMIT ").append(query.getLimit());
        }

        return sb.toString();
    }

    private String joinCriteria(List<Criteria> criteria, AndOr andOr) {

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

    private String joinCriteriaSet(List<Criterion> criteriaSets, AndOr andOr) {
        final StringBuilder sb = new StringBuilder();

        if (criteriaSets.size() > 1) {
            sb.append("(");
        }

        sb.append(
                criteriaSets.stream()
                        .map(criterion -> {
                            final Class javaType = getPropertyJavaType(criterion.property());
                            CriterionOperation criterionOperation = criterionOperationRegistry.getCriterionOperation(criterion.operation(), javaType);
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
