package org.ifinal.finalframework.data.query.criterion;

import lombok.Getter;
import org.ifinal.finalframework.data.query.operation.JsonOperation;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class JsonContainsCriterionImpl extends BaseCriterion implements JsonContainsCriterion {

    @Getter
    private final Object target;

    @Getter
    private final JsonOperation operation;

    @Getter
    private final Object value;

    @Getter
    private final String path;

    JsonContainsCriterionImpl(final Object target, final JsonOperation operation, final Object value,
        final String path) {

        this.target = target;
        this.operation = operation;
        this.value = value;
        this.path = path;
    }

    @SuppressWarnings("unused")
    public String getCriterionTarget() {
        return CriterionTarget.from(target).toString();
    }

    public String getCriterionValue() {
        final String criterionValue =
            this.value instanceof CriterionValue ? "criterion.value.value" : "criterion.value";
        return ((CriterionValueImpl<?>) CriterionValue.from(this.value)).getSqlExpression(criterionValue);
    }

    /**
     * <pre>
     *     <code>
     *         <trim prefix="JSON_CONTAINS(" suffix=")">
     *              property,
     *              #{value},
     *              <if test="path != null">
     *                  #{path}
     *              </if>
     *         </trim>
     *     </code>
     * </pre>
     *
     * @param sql        sql
     * @param expression expression
     */

    @Override
    public void apply(final @NonNull StringBuilder sql, final @NonNull String expression) {

        sql.append("<trim prefix=\"");
        switch (operation) {
            case JSON_CONTAINS:
                sql.append("JSON_CONTAINS(");
                break;

            case NOT_JSON_CONTAINS:
                sql.append("!JSON_CONTAINS(");
                break;

            default:
                throw new IllegalArgumentException("JsonContains not support operation of " + operation.name());
        }
        sql.append("\" suffix=\")\">");

        applyValueCriterion(sql, target, null, null, expression + ".target");
        applyValueCriterion(sql, value, ",", "", expression + ".value");

        sql.append(String.format("<if test=\"%s.path != null\">", expression))
            .append(",#{").append(expression).append(".path}")
            .append("</if>");

        sql.append("</trim>");

    }

    @Override
    public String toString() {
        if (JsonOperation.JSON_CONTAINS == operation) {
            return String
                .format("JSON_CONTAINS(%s,%s,'%s')", CriterionValue.from(getTarget()), CriterionValue.from(getValue()),
                    path);
        } else if (JsonOperation.NOT_JSON_CONTAINS == operation) {
            return String
                .format("!JSON_CONTAINS(%s,%s,'%s')", CriterionValue.from(getTarget()), CriterionValue.from(getValue()),
                    path);
        }
        throw new UnsupportedOperationException(operation.name());
    }

}

