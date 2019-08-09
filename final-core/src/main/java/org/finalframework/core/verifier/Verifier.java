package org.finalframework.core.verifier;

/**
 * 业务数据校验器
 *
 * @author likly
 * @version 1.0
 * @date 2019-04-26 14:05:18
 * @since 1.0
 */
@FunctionalInterface
public interface Verifier<T> {
    /**
     * 校验业务数据是否合法，合法返回 {@code true}，否则返回 {@code false}。
     *
     * @param data 业务数据
     * @return 返回业务数据是否合法。
     */
    boolean verify(T data);
}
