package cn.com.likly.finalframework.core;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 20:54:20
 * @since 1.0
 */
@FunctionalInterface
public interface Converter<SOURCE, TARGET> {
    TARGET convert(SOURCE source);
}
