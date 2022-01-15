package org.ifinalframework.poi;

import org.ifinalframework.poi.type.*;
import org.ifinalframework.poi.type.ObjectTypeHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.2.4
 **/
public class TypeHandlerRegistry {
    private final Map<Class<?>, TypeHandler<?>> typeHandlerMap = new LinkedHashMap<>();

    public TypeHandlerRegistry() {

        register(boolean.class, new BooleanTypeHandler());
        register(Boolean.class, new BooleanTypeHandler());

        register(String.class, new StringTypeHandler());

        register(short.class, new ShortTypeHandler());
        register(Short.class, new ShortTypeHandler());
        register(int.class, new IntegerTypeHandler());
        register(Integer.class, new IntegerTypeHandler());
        register(long.class, new LongTypeHandler());
        register(Long.class, new LongTypeHandler());

        register(float.class, new FloatTypeHandler());
        register(Float.class, new FloatTypeHandler());
        register(double.class, new DoubleTypeHandler());
        register(Double.class, new DoubleTypeHandler());

        register(Date.class, new DateTypeHandler());
        register(Object.class,new ObjectTypeHandler());
    }

    public <T> void register(Class<T> javaType, TypeHandler<T> typeHandler) {
        typeHandlerMap.put(javaType, typeHandler);
    }

    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType) {
        return (TypeHandler<T>) typeHandlerMap.get(javaType);
    }

}
