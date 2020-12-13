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
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.rawType = (Class<T>) genericSuperclass.getRawType();
        this.type = genericSuperclass.getActualTypeArguments()[0];
    }

    public Class<T> getRawType() {
        return rawType;
    }

    public final Type getType() {
        return type;
    }

}
