

package org.finalframework.core.converter;

import java.util.function.Function;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 20:54:20
 * @see org.springframework.core.convert.converter.Converter
 * @see java.util.function.Function
 * @since 1.0
 */
@FunctionalInterface
public interface Converter<T, R> extends Function<T, R>, org.springframework.core.convert.converter.Converter<T, R> {
    /**
     * Applies this function to the given argument.
     *
     * @param source the function argument
     * @return the function result
     */
    @Override
    default R apply(T source) {
        return convert(source);
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     */
    @Override
    R convert(T source);
}
