package org.finalframework.annotation;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.NonNull;

/**
 * The interface should be impl by the {@linkplain Enum enum} which want to be a stronger enum.
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
    @JsonValue
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
