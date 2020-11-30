package org.ifinal.finalframework.util;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class Reflections {
    private Reflections() {
    }

    @Nullable
    public static Field findField(@NonNull Class<?> clazz, @NonNull String name) {
        return ReflectionUtils.findField(clazz, name);
    }

    @NonNull
    public static Field findRequiredField(@NonNull Class<?> clazz, @NonNull String name) {
        final Field field = findField(clazz, name);
        Objects.requireNonNull(field, String.format("can not find field of name '%s' on class '%s'", name, clazz));
        return field;
    }


    public static Class findParameterizedClassArgumentClass(@NonNull Class<?> target, @NonNull Class<?> targetInterface, int index) {
        Type type = target.getGenericSuperclass();
        if (type instanceof ParameterizedType && targetInterface.isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
            return (Class) ((ParameterizedType) type).getActualTypeArguments()[index];
        }
        throw new IllegalArgumentException("");
    }

    public static Class findParameterizedInterfaceArgumentClass(@NonNull Class<?> target, @NonNull Class<?> targetInterface, int index) {
        final Type[] genericInterfaces = target.getGenericInterfaces();

        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType && targetInterface.isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
                return (Class) ((ParameterizedType) type).getActualTypeArguments()[index];
            }
        }

        throw new IllegalArgumentException("");
    }
}
