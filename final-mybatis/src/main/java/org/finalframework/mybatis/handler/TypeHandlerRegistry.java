/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.mybatis.handler;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.annotation.data.PersistentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 13:01
 * @since 1.0
 */
public class TypeHandlerRegistry {
    private static final Logger logger = LoggerFactory.getLogger(TypeHandlerRegistry.class);
    private static final TypeHandlerRegistry INSTANCE = new TypeHandlerRegistry();
    private final TypeHandlerModule typeHandlerModule = TypeHandlerModule.DEFAULT;

    private TypeHandlerRegistry() {
    }

    public static TypeHandlerRegistry getInstance() {
        return INSTANCE;
    }

    public <T> void registerTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType, PersistentType persistentType, TypeHandler<?> typeHandler) {
        typeHandlerModule.registerTypeHandler(javaType, collectionType, persistentType, typeHandler);
    }

    @SuppressWarnings("unchecked")
    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType, PersistentType persistentType) {
        if (javaType.isEnum()) {
            return new EnumTypeHandler(javaType);
        }

        TypeHandler<T> typeHandler = typeHandlerModule.getTypeHandler(javaType, collectionType, persistentType);
        logger.trace("find typeHandler: ,javaType={},collectionType={},persistentType={},typeHandler={}",
                javaType, collectionType, persistentType.getClass(), typeHandler == null ? null : typeHandler.getClass().getCanonicalName());
        return typeHandler;

    }

}
