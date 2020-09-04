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

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 12:43:08
 * @since 1.0
 */
@SuppressWarnings("all")
public class JsonPersistentTypeHandlerRegister extends PersistentTypeHandlerRegister {
    @Override
    public <T> TypeHandler<T> getTypeHandler(Class<T> javaType, Class<? extends Collection> collectionType) {

        TypeHandler<T> typeHandler = super.getTypeHandler(javaType, collectionType);

        if (typeHandler == null) {
            typeHandler = (TypeHandler<T>) initTypeHandler(javaType,collectionType);
            registerTypeHandler(javaType,collectionType,typeHandler);
        }
        return typeHandler;
    }

    private <T> TypeHandler<?> initTypeHandler(Class<T> javaType,Class<? extends Collection> collectionType){
        if (collectionType == null) {
            return new JsonObjectTypeHandler<>(javaType);
        }

        if (List.class.isAssignableFrom(collectionType)) {
            return new JsonListTypeHandler<>(javaType);
        }

        if (Set.class.isAssignableFrom(collectionType)) {
            return  new JsonSetTypeHandler<>(javaType);
        }

        throw new IllegalArgumentException("");

    }
}
