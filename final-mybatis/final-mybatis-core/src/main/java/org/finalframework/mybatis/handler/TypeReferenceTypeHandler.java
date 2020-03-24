package org.finalframework.mybatis.handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.apache.ibatis.type.BaseTypeHandler;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-24 20:17:38
 * @since 1.0
 */
public abstract class TypeReferenceTypeHandler<T> extends BaseTypeHandler<T> {

    private final Type type;

    public TypeReferenceTypeHandler() {
        this.type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Type getType() {
        return type;
    }
}
