package org.ifinal.finalframework.data.query.criterion.function;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface StringFunction<R> {

    /**
     * 为目标添加上前缀 {@code prefix} 和 后缀 {@code suffix}
     *
     * @param prefix 前缀
     * @param suffix 后续
     * @return result
     */
    R contact(String prefix, String suffix);

}
