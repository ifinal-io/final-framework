
package org.ifinal.finalframework.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class BaseTypeReferenceTypeHandler<T> extends BaseTypeHandler<T> {

    private final Type type;

    public BaseTypeReferenceTypeHandler(Type type) {
        this.type = type;
    }

    @SuppressWarnings("unused")
    public BaseTypeReferenceTypeHandler() {
        this.type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Type getType() {
        return type;
    }
}
