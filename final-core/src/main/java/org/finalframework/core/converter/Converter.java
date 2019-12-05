package org.finalframework.core.converter;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 20:54:20
 * @see org.springframework.core.convert.converter.Converter
 * @since 1.0
 */
@FunctionalInterface
public interface Converter<S, T> extends org.springframework.core.convert.converter.Converter<S, T> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     */
    @Override
    T convert(S source);
}
