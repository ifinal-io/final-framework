package com.ilikly.finalframework.core.converter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-08 13:22:13
 * @since 1.0
 */
@FunctionalInterface
public interface Converter3<S1, S2, S3, TARGET> {

    /**
     * convert to {@link TARGET} with {@link S1} , {@link S2} and {@link S3}
     */
    TARGET map(S1 s1, S2 s2, S3 s3);
}
