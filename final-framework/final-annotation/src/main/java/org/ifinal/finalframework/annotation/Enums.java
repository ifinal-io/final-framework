package org.ifinal.finalframework.annotation;


import org.ifinal.finalframework.annotation.data.EnumValue;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Enums {
    private Enums() {
    }

    public static <E extends Enum<E>> E findEnum(@NonNull EnumValue ann, @NonNull Object value) {
        return findEnum(ann.value(), ann.creator(), ann.valueType(), value);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <E extends Enum<E>> E findEnum(@NonNull Class<? extends Enum> clazz, @NonNull String creator, @Nullable Class<?> valueType, @NonNull Object value) {
        Method valueOf = Objects.isNull(valueType) ? ReflectionUtils.findMethod(clazz, creator) : ReflectionUtils.findMethod(clazz, creator, valueType);

        if (valueOf == null) {
            throw new IllegalArgumentException("can not find method named " + creator + " as enum class " + clazz.getCanonicalName());
        }

        return (E) ReflectionUtils.invokeMethod(valueOf, clazz, value);
    }

    public static String getEnumI18NCode(Enum<?> value) {
        return String.format("%s.%s", value.getClass().getCanonicalName(), value.name().toLowerCase(Locale.ENGLISH));
    }


}

