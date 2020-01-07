package org.finalframework.data.query.criterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:49:06
 * @since 1.0
 */
public class BetweenCriterionImpl<T> extends AbsCriterion<T> implements BetweenCriterion<T> {
    private final T min;
    private final T max;

    private BetweenCriterionImpl(BuilderImpl<T> builder) {
        super(builder);
        this.min = builder.min;
        this.max = builder.max;
    }

    public static <T> BetweenCriterion.Builder<T> builder() {
        return new BuilderImpl<>();
    }

    @Override
    public T min() {
        return min;
    }

    @Override
    public T max() {
        return max;
    }


    public String getFunctionMin() {
        String expression = getTypeHandler() == null ? "#{criterion.min}" : String.format("#{criterion.min, typeHandler=%s}", getTypeHandler().getCanonicalName());
        return getFunctionValue(expression);
    }

    public String getFunctionMax() {
        String expression = getTypeHandler() == null ? "#{criterion.max}" : String.format("#{criterion.max, typeHandler=%s}", getTypeHandler().getCanonicalName());
        return getFunctionValue(expression);
    }


    private static class BuilderImpl<T> extends AbsBuilder<BetweenCriterion<T>, BetweenCriterion.Builder<T>> implements BetweenCriterion.Builder<T> {

        private T min;
        private T max;

        @Override
        public BetweenCriterion.Builder<T> between(T min, T max) {
            this.min = min;
            this.max = max;
            return this;
        }

        @Override
        public BetweenCriterion<T> build() {
            return new BetweenCriterionImpl<>(this);
        }
    }
}
