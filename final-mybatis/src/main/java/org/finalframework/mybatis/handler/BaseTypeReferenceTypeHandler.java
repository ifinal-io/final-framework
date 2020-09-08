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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.apache.ibatis.type.BaseTypeHandler;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-24 20:17:38
 * @since 1.0
 */
public abstract class BaseTypeReferenceTypeHandler<T> extends BaseTypeHandler<T> {

    private final Type type;

    public BaseTypeReferenceTypeHandler(Type type) {
        this.type = type;
    }

    public BaseTypeReferenceTypeHandler() {
        this.type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Type getType() {
        return type;
    }
}