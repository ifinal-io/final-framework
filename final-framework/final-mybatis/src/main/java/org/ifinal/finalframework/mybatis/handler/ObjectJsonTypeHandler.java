package org.ifinal.finalframework.mybatis.handler;


/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ObjectJsonTypeHandler extends JsonObjectTypeHandler<Object> {
    public ObjectJsonTypeHandler() {
        super(Object.class);
    }

    @Override
    protected Object getNullableResult(String json) {
        throw new UnsupportedOperationException("");
    }
}

