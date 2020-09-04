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
import org.finalframework.data.annotation.enums.PersistentType;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 12:17:32
 * @since 1.0
 */
public class TypeHandlerModule {

    public static final TypeHandlerModule DEFAULT = new TypeHandlerModule() {
        {
            registerPersistentTypeHandlerRegister(PersistentType.AUTO, PersistentTypeHandlerRegister.AUTO);
            registerPersistentTypeHandlerRegister(PersistentType.JSON, new JsonPersistentTypeHandlerRegister());
        }
    };
    private final Map<PersistentType, PersistentTypeHandlerRegister> typeHandlerRegisterMap = new ConcurrentHashMap<>();

    public <T> void registerTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType, PersistentType persistentType, TypeHandler<?> typeHandler) {
        PersistentTypeHandlerRegister persistentTypeHandlerRegister = getPersistentTypeHandlerRegister(persistentType);
        if (persistentTypeHandlerRegister != null) {
            persistentTypeHandlerRegister.registerTypeHandler(javaType, collectionType, typeHandler);
        }
    }

    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType, PersistentType persistentType) {
        PersistentTypeHandlerRegister persistentTypeHandlerRegister = getPersistentTypeHandlerRegister(persistentType);
        if (persistentTypeHandlerRegister != null) {
            return persistentTypeHandlerRegister.getTypeHandler(javaType, collectionType);
        }
        return null;
    }

    public PersistentTypeHandlerRegister getPersistentTypeHandlerRegister(PersistentType type) {
        return typeHandlerRegisterMap.get(type);
    }

    public void registerPersistentTypeHandlerRegister(PersistentType type, PersistentTypeHandlerRegister register) {
        typeHandlerRegisterMap.put(type, register);
    }

}
