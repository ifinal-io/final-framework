package org.ifinal.finalframework.data.query.criterion;

import lombok.Getter;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public final class AbsCompareCriterionOperation extends BaseCriterion implements CompareCriterionOperation {

    public static final String TARGET_EXPRESSION = ".target";

    public static final String VALUE_EXPRESSION = ".value";

    private final Object target;

    private final CompareOperation operation;

    private final Object value;

    private final Object min;

    private final Object max;

    private AbsCompareCriterionOperation(final AbsCompareCriterionOperationBuilder builder) {

        this.target = builder.target;
        this.operation = builder.operation;
        this.value = builder.value;
        this.min = builder.min;
        this.max = builder.max;
    }

    static CompareCriterionOperationBuilder builder() {
        return new AbsCompareCriterionOperationBuilder();
    }

    @Override
    public void apply(final @NonNull StringBuilder sql, final @NonNull String expression) {

        switch (operation) {
            case NULL:
                applyValueCriterion(sql, target, null, "IS NULL", expression + TARGET_EXPRESSION);
                break;
            case NOT_NULL:
                applyValueCriterion(sql, target, null, "IS NOT NULL", expression + TARGET_EXPRESSION);
                break;
            case EQUAL:
                applyValueCriterion(sql, target, null, null, expression + TARGET_EXPRESSION);
                applyValueCriterion(sql, value, " = ", null, expression + VALUE_EXPRESSION);
                break;
            case NOT_EQUAL:
                applyValueCriterion(sql, target, null, null, expression + TARGET_EXPRESSION);
                applyValueCriterion(sql, value, " != ", null, expression + VALUE_EXPRESSION);
                break;
            case GREAT_THAN:
                applyValueCriterion(sql, target, null, null, expression + TARGET_EXPRESSION);
                applyValueCriterion(sql, value, " > ", null, expression + VALUE_EXPRESSION);
                break;
            case GREAT_THAN_EQUAL:
                applyValueCriterion(sql, target, null, null, expression + TARGET_EXPRESSION);
                applyValueCriterion(sql, value, " >= ", null, expression + VALUE_EXPRESSION);
                break;
            case LESS_THAN:
                applyValueCriterion(sql, target, null, null, expression + TARGET_EXPRESSION);
                applyValueCriterion(sql, value, " < ", null, expression + VALUE_EXPRESSION);
                break;
            case LESS_THAN_EQUAL:
                applyValueCriterion(sql, target, null, null, expression + TARGET_EXPRESSION);
                applyValueCriterion(sql, value, " <= ", null, expression + VALUE_EXPRESSION);
                break;
            case LIKE:
                applyValueCriterion(sql, target, null, null, expression + TARGET_EXPRESSION);
                applyValueCriterion(sql, value, " LIKE ", null, expression + VALUE_EXPRESSION);
                break;
            case NOT_LIKE:
                applyValueCriterion(sql, target, null, null, expression + TARGET_EXPRESSION);
                applyValueCriterion(sql, value, " NOT LIKE ", null, expression + VALUE_EXPRESSION);
                break;
            case IN:
                applyValueCriterion(sql, target, null, null, expression + TARGET_EXPRESSION);
                applyValueCriterion(sql, value, " IN (", ")", expression + VALUE_EXPRESSION);
                break;
            case NOT_IN:
                applyValueCriterion(sql, target, null, null, expression + TARGET_EXPRESSION);
                applyValueCriterion(sql, value, " NOT IN (", ")", expression + VALUE_EXPRESSION);
                break;
            case BETWEEN:
                applyValueCriterion(sql, target, null, null, expression + TARGET_EXPRESSION);
                applyValueCriterion(sql, value, " BETWEEN ", null, expression + ".min");
                applyValueCriterion(sql, value, " AND ", null, expression + ".max");
                break;
            case NOT_BETWEEN:
                applyValueCriterion(sql, target, null, null, expression + TARGET_EXPRESSION);
                applyValueCriterion(sql, value, " NOT BETWEEN ", null, expression + ".min");
                applyValueCriterion(sql, value, " AND ", null, expression + ".max");
                break;
            default:
                throw new IllegalArgumentException(operation.name());

        }

    }

    private static class AbsCompareCriterionOperationBuilder implements CompareCriterionOperation.CompareCriterionOperationBuilder {

        private Object target;

        private CompareOperation operation;

        private Object value;

        private Object min;

        private Object max;

        @Override
        public CompareCriterionOperationBuilder target(final Object target) {

            this.target = target;
            return this;
        }

        @Override
        public CompareCriterionOperationBuilder operation(final CompareOperation operation) {

            this.operation = operation;
            return this;
        }

        @Override
        public CompareCriterionOperationBuilder value(final Object value) {

            this.value = value;
            return this;
        }

        @Override
        public CompareCriterionOperationBuilder min(final Object min) {

            this.min = min;
            return this;
        }

        @Override
        public CompareCriterionOperationBuilder max(final Object max) {

            this.max = max;
            return this;
        }

        @Override
        public CompareCriterionOperation build() {
            return new AbsCompareCriterionOperation(this);
        }

    }

}

