package org.ifinal.finalframework.annotation.aop;

import org.ifinal.finalframework.annotation.core.lang.Transient;

/**
 * AOP切入点，用于描述缓存 {@link java.lang.annotation.Annotation} 的调用顺序。
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Transient
public enum JoinPoint {
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
    AFTER_THROWING,
    /**
     * 在方法执行之后，包含 {@link #AFTER_RETURNING} 和 {@link #AFTER_THROWING}。
     */
    AFTER

}
