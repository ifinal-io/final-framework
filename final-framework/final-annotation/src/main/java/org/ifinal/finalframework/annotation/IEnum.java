package org.ifinal.finalframework.annotation;

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


    @Nullable
    @SuppressWarnings("unused")
    static <T extends IEnum<?>> T valueOf(@NonNull Class<T> type, @NonNull Object code) {
        T[] constants = type.getEnumConstants();
        for (T constant : constants) {
            if (Objects.equals(constant.getCode(), code)) {
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
