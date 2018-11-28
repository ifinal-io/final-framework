package cn.com.likly.finalframework.core;

import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 10:31
 * @since 1.0
 */
@FunctionalInterface
public interface Streable<T> {
    Stream<T> stream();
}
