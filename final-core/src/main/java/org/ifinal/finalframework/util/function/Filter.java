package org.ifinal.finalframework.util.function;

import java.util.function.Predicate;

/**
 * 数据过滤器，判断数据是否匹配，用于过滤数据集中不符合目标数据的数据。
 *
 * @author likly
 * @version 1.0.0
 * @see java.util.function.Predicate
 * @since 1.0.0
 */
@FunctionalInterface
public interface Filter<T> extends Predicate<T> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param data the input argument
     * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
     */
    @Override
    default boolean test(T data) {
        return matches(data);
    }

    /**
     * return true if the test data {@link T} is matches, else return false.
     *
     * @param data the data to test
     * @return {@code true} if the test data {@link T} is matches, else return {@code false}.
     */
    boolean matches(T data);

}
