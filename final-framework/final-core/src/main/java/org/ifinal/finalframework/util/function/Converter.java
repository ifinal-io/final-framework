package org.ifinal.finalframework.util.function;

import org.springframework.lang.Nullable;

import java.util.function.Function;

/**
 * @author likly
 * @version 1.0.0
 * @see org.springframework.core.convert.converter.Converter
 * @see java.util.function.Function
 * @since 1.0.0
 */
@FunctionalInterface
public interface Converter<T, R> extends Function<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param source the function argument
     * @return the function result
     */
    @Override
    @Nullable
    default R apply(final @Nullable T source) {

        return convert(source);
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} ({@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     */
    @Nullable
    R convert(final @Nullable T source);

}
