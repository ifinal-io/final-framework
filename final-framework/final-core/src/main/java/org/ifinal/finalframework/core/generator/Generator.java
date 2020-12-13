package org.ifinal.finalframework.core.generator;

/**
 * 生成器
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Generator<T, R> {

    R generate(final T data);

}
