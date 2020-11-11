package org.finalframework.annotation;

import org.springframework.lang.NonNull;

/**
 * A maker superinterface which the {@linkplain Enum enum} should be impl.
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:07
 * @since 1.0
 */
public interface IEnum<T> {

    /**
     * return the enum code, not the {@linkplain Enum#ordinal() ordinal}.
     *
     * @return enum code
     */
//    @JsonValue
    @NonNull
    T getCode();

    /**
     * return the enum desc, not the {@linkplain Enum#name() name}.
     *
     * @return enum desc
     */
    @NonNull
    String getDesc();
}
