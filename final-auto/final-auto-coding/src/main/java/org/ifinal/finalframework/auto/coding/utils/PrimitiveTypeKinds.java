package org.ifinal.finalframework.auto.coding.utils;


import org.springframework.lang.NonNull;

import javax.lang.model.type.TypeKind;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class PrimitiveTypeKinds {

    private static final Map<TypeKind, Class<?>> PRIMITIVE_TYPES = new HashMap<>();

    static {
        PRIMITIVE_TYPES.put(TypeKind.BYTE, Byte.class);
        PRIMITIVE_TYPES.put(TypeKind.SHORT, Short.class);

        PRIMITIVE_TYPES.put(TypeKind.CHAR, Character.class);
        PRIMITIVE_TYPES.put(TypeKind.BOOLEAN, Boolean.class);

        PRIMITIVE_TYPES.put(TypeKind.INT, Integer.class);
        PRIMITIVE_TYPES.put(TypeKind.LONG, Long.class);

        PRIMITIVE_TYPES.put(TypeKind.FLOAT, Float.class);
        PRIMITIVE_TYPES.put(TypeKind.DOUBLE, Double.class);
    }

    public static Class<?> getPrimitiveTypeKind(@NonNull TypeKind kind) {
        return PRIMITIVE_TYPES.get(kind);
    }
}

