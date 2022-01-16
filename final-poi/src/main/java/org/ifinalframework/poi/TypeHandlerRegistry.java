/*
 * Copyright 2020-2022 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.poi;

import org.ifinalframework.poi.type.ObjectTypeHandler;
import org.ifinalframework.poi.type.*;

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
        register(Object.class, new ObjectTypeHandler());
    }

    public <T> void register(Class<T> javaType, TypeHandler<T> typeHandler) {
        typeHandlerMap.put(javaType, typeHandler);
    }

    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType) {
        return (TypeHandler<T>) typeHandlerMap.get(javaType);
    }

}
