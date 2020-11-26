package org.ifinal.finalframework.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class TypeReference<T> {
    private final Type type;

    public TypeReference() {
        this.type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public final Type getType() {
        return type;
    }
}
