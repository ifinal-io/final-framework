package org.ifinal.finalframework.annotation;

import org.ifinal.finalframework.annotation.data.Transient;
import org.springframework.lang.NonNull;

/**
 * A maker superinterface which the {@linkplain Enum enum} should be impl.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Transient
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
