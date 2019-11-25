package org.finalframework.data.query.criterion;


import org.finalframework.core.Assert;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-25 15:10:12
 * @since 1.0
 */
public class LikeCriterionImpl<T> extends AbsCriterion<T> implements LikeCriterion<T> {

    private final String prefix;
    private final T value;
    private final String suffix;

    private LikeCriterionImpl(BuilderImpl<T> builder) {
        super(builder);
        this.prefix = Assert.isNull(builder.prefix) ? "" : builder.prefix;
        this.suffix = Assert.isNull(builder.suffix) ? "" : builder.suffix;
        this.value = builder.value;
    }

    public static <T> LikeCriterion.Builder<T> builder() {
        return new BuilderImpl<>();
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    private static class BuilderImpl<T> extends AbsBuilder<LikeCriterion<T>, LikeCriterion.Builder<T>>
            implements LikeCriterion.Builder<T> {
        private String prefix;
        private T value;
        private String suffix;


        @Override
        public LikeCriterion.Builder<T> like(String prefix, T value, String suffix) {
            this.prefix = prefix;
            this.value = value;
            this.suffix = suffix;
            return this;
        }

        @Override
        public LikeCriterion<T> build() {
            return new LikeCriterionImpl<>(this);
        }
    }
}

