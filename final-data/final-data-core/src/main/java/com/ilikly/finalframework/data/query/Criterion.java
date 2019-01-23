package com.ilikly.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 12:19:55
 * @since 1.0
 */
public interface Criterion<T> {
    static <T> Builder<T> builder() {
        return CriterionImpl.builder();
    }

    QProperty property();

    String operation();

    T value();

    T min();

    T max();

    interface Builder<T> extends com.ilikly.finalframework.core.Builder<Criterion> {
        Builder<T> property(QProperty property);

        Builder<T> operation(String operation);

        Builder<T> value(T value);

        Builder<T> minAndMax(T min, T max);

    }

}
