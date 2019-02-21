package com.ilikly.finalframework.core.converter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-08 13:22:13
 * @since 1.0
 */
@FunctionalInterface
public interface Converter2<S1, S2, TARGET> {

    /**
     * convert to {@link TARGET} with {@link S1} and {@link S2}
     */
    TARGET convert(S1 s1, S2 s2);
}
