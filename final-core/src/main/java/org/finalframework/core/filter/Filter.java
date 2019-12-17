package org.finalframework.core.filter;

/**
 * 数据过滤器，判断数据是否匹配，用于过滤数据集中不符合目标数据的数据。
 *
 * @author likly
 * @version 1.0
 * @date 2018-11-28 21:53:49
 * @since 1.0
 */
@FunctionalInterface
public interface Filter<T> {
    /**
     * return true if the test data {@link T} is matches, else return false.
     *
     * @param t the data to test
     * @return {@code true} if the test data {@link T} is matches, else return {@code false}.
     */
    boolean matches(T t);
}
