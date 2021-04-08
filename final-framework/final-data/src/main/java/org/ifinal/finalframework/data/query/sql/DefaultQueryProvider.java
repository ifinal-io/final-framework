package org.ifinal.finalframework.data.query.sql;

import org.ifinal.finalframework.data.query.Query;
import org.ifinal.finalframework.data.query.criterion.VelocityCriterionValue;
import org.ifinal.finalframework.data.util.Velocities;
import org.ifinal.finalframework.query.AndOr;
import org.ifinal.finalframework.query.Criteria;
import org.ifinal.finalframework.query.Criterion;
import org.ifinal.finalframework.query.CriterionAttributes;

/**
 * DefaultQueryProvider.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultQueryProvider extends AbsQueryProvider {

    private final Query query;

    public DefaultQueryProvider(final Query query) {
        this.query = query;
    }

    @Override
    public String where() {

        final StringBuilder sql = new StringBuilder();

        sql.append("<where>");

        appendCriteria(sql, query.getCriteria(), AndOr.AND, "query.criteria");

        sql.append("</where>");

        return sql.toString();

    }

    private void appendCriteria(StringBuilder sql, Criteria criteria, AndOr andOr, String expression) {
        for (int i = 0; i < criteria.size(); i++) {
            Criterion criterion = criteria.get(i);

            if (criterion instanceof CriterionAttributes) {
                CriterionAttributes attributes = ((CriterionAttributes) criterion);

                CriterionAttributes target = new CriterionAttributes();
                target.putAll(attributes);
                target.put("criterion", String.format("%s[%d]", expression, i));

                String column = target.getColumn();

                if (column.contains("${") || column.contains("#{")) {
                    column = Velocities.getValue(column, target);
                    target.setColumn(column);
                }

                target.put(CriterionAttributes.ATTRIBUTE_NAME_AND_OR, andOr);
                target.put(CriterionAttributes.ATTRIBUTE_NAME_VALUE, String.format("%s[%d].value", expression, i));

                String value = new VelocityCriterionValue(attributes.getString(CriterionAttributes.ATTRIBUTE_NAME_EXPRESSION))
                    .value(target);
                sql.append(value);
            } else if (criterion instanceof Criteria) {

                sql.append("<trim prefix=\"").append(andOr).append(" (\" suffix=\")\" prefixOverrides=\"AND |OR\">");

                Criteria loopCriteria = (Criteria) criterion;
                appendCriteria(sql, loopCriteria, loopCriteria.getAndOr(), String.format("%s[%d]", expression, i));

                sql.append("</trim>");

            }

        }
    }

}
