package org.finalframework.core.verifier;

/**
 * 业务校验器
 *
 * @author likly
 * @version 1.0
 * @date 2019-04-26 14:05:18
 * @since 1.0
 */
public interface Verifier<T> {
    void verify(T data);
}
