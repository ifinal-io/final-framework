package org.finalframework.data.query.criterion.function;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-25 13:03:52
 * @since 1.0
 */
public interface StringFunction<V, R> {
    /**
     * 为目标添加上前缀 {@code prefix} 和 后缀 {@code suffix}
     *
     * @param prefix 前缀
     * @param suffix 后续
     */
    R contact(String prefix, String suffix);
}
