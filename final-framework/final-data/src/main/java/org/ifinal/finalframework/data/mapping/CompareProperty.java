package org.ifinal.finalframework.data.mapping;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CompareProperty {

    static Builder builder() {
        return new BaseCompareProperty.BuilderImpl();
    }

    Property property();

    Object beforeValue();

    Object afterValue();

    boolean equals();

    interface Builder extends org.ifinal.finalframework.util.Builder<CompareProperty> {

        Builder property(final Property property);

        Builder value(final Object before, final Object after);

    }

}
