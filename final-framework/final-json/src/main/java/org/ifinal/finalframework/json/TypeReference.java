package org.ifinal.finalframework.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class TypeReference<T> {

    private final Class<T> rawType;

    private final Type type;

    @SuppressWarnings("unchecked")
    protected TypeReference() {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.type = genericSuperclass.getActualTypeArguments()[0];
        if (type instanceof ParameterizedType) {
            this.rawType = (Class<T>) ((ParameterizedType) type).getRawType();
        } else {
            this.rawType = (Class<T>) type;
        }

    }

    public Class<T> getRawType() {
        return rawType;
    }

    public final Type getType() {
        return type;
    }

}
