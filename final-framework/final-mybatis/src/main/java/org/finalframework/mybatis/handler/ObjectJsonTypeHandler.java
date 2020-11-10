package org.finalframework.mybatis.handler;


/**
 * @author likly
 * @version 1.0
 * @date 2020-06-11 09:59:23
 * @since 1.0
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

