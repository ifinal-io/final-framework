package org.finalframework.cache.annotation.enums;

/**
 * 缓存调用时机，用于描述缓存 {@link java.lang.annotation.Annotation} 的调用时机。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-10 22:14:18
 * @since 1.0
 */
public enum InvocationTime {
    /**
     * 在方法执行之前
     */
    BEFORE,
    /**
     * 在方法返回之后
     */
    AFTER_RETURNING,
    /**
     * 在方法抛出异常后
     */
    AFTER_THROWING;
}
