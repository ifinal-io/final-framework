package com.ilikly.finalframework.core.converter;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 20:54:20
 * @since 1.0
 */
@FunctionalInterface
public interface Converter<SOURCE, TARGET> {
    /**
     * converter the {@link SOURCE} to the {@link TARGET}
     */
    TARGET map(SOURCE source);
}
