package org.finalframework.data.mapping;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-13 20:12:25
 * @since 1.0
 */
public class BaseCompareProperty implements CompareProperty {
    private final Property property;
    private final Object beforeValue;
    private final Object afterValue;
    private final boolean equals;

    private BaseCompareProperty(BuilderImpl builder) {
        this.property = builder.property;
        this.beforeValue = builder.beforeValue;
        this.afterValue = builder.afterValue;

        if (beforeValue == null && afterValue == null) {
            this.equals = false;
        } else if (beforeValue == null || afterValue == null) {
            this.equals = true;
        } else {
            this.equals = beforeValue.equals(afterValue);
        }

    }

    @Override
    public Property property() {
        return this.property;
    }

    @Override
    public Object beforeValue() {
        return this.beforeValue;
    }

    @Override
    public Object afterValue() {
        return this.afterValue;
    }

    @Override
    public boolean equals() {
        return this.equals;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(property.getName()).append("[beforeValue=")
                .append(beforeValue)
                .append(",afterValue=")
                .append(afterValue)
                .append(",equals=")
                .append(equals)
                .append("]");
        return builder.toString();
    }

    public static class BuilderImpl implements CompareProperty.Builder {
        private Property property;
        private Object beforeValue;
        private Object afterValue;

        @Override
        public Builder property(Property property) {
            this.property = property;
            return this;
        }

        @Override
        public Builder value(Object before, Object after) {
            this.beforeValue = before;
            this.afterValue = after;
            return this;
        }

        @Override
        public CompareProperty build() {
            return new BaseCompareProperty(this);
        }
    }
}
