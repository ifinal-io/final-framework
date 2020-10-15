package org.finalframework.aop.annotation;

import org.finalframework.annotation.data.Transient;

/**
 * AOP切入点，用于描述缓存 {@link java.lang.annotation.Annotation} 的调用顺序。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-10 22:14:18
 * @since 1.0
 */
@Transient
public enum CutPoint {
    /**
     * 在方法执行之前
     */
    BEFORE(1),
    /**
     * 在方法返回之后
     */
    AFTER_RETURNING(2),
    /**
     * 在方法抛出异常后
     */
    AFTER_THROWING(4),
    /**
     * 在方法执行之后，包含 {@link #AFTER_RETURNING} 和 {@link #AFTER_THROWING}。
     */
    AFTER(6);

    private final Integer value;

    CutPoint(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
