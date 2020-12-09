package org.ifinal.finalframework.annotation.core;

import org.ifinal.finalframework.annotation.data.Transient;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;

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
     * return the {@link IEnum} value of {@code code}
     * @param type the enum type
     * @param code the enum code
     * @param <T> enum type
     * @return enum value of code
     */
    @Nullable
    static <T extends IEnum<?>> T valueOf(@NonNull Class<T> type, @NonNull Object code) {
        T[] constants = type.getEnumConstants();
        for (T constant : constants) {
            if (Objects.equals(constant.getCode().toString(), code.toString())) {
                return constant;
            }
        }
        return null;
    }


    /**
     * return the enum code, not the {@linkplain Enum#ordinal() ordinal}.
     *
     * @return enum code
     */
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
