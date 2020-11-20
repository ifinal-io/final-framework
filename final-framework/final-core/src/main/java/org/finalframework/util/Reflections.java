package org.finalframework.util;

import org.springframework.lang.NonNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/16 14:02:51
 * @since 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class Reflections {
    private Reflections() {
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
