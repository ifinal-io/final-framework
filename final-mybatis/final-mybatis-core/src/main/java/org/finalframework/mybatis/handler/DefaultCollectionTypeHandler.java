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

import org.finalframework.core.PrimaryTypes;

import java.util.AbstractCollection;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 10:22
 * @since 1.0
 */
public abstract class DefaultCollectionTypeHandler<E, T extends Collection<E>> extends CollectionTypeHandler<E, T> {

    public DefaultCollectionTypeHandler(Class<E> type) {
        super(type);
    }

    @Override
    protected String setParameter(T parameter) {

        if (parameter == null || parameter.isEmpty()) return null;

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (E item : parameter) {
            if (i++ != 0) {
                sb.append(",");
            }
            sb.append(item.toString());
        }

        return sb.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected final T getResult(String value, Class<E> type) {

        if (value == null || value.trim().isEmpty()) return null;

        AbstractCollection result = (AbstractCollection) getCollection();

        for (String item : value.split(",")) {
            if (PrimaryTypes.isString(type)) {
                result.add(item);
            } else if (PrimaryTypes.isInteger(type)) {
                result.add(Integer.parseInt(item));
            } else if (PrimaryTypes.isLong(type)) {
                result.add(Long.parseLong(item));
            } else if (PrimaryTypes.isBoolean(type)) {
                result.add(Boolean.parseBoolean(item));
            } else {
                throw new IllegalArgumentException("the target type eq not support " + type.getName());
            }
        }

        return (T) result;
    }

    protected abstract T getCollection();

}
