/*
 * Copyright 2020-2021 the original author or authors.
 *
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

package org.ifinalframework.data.query;

import org.ifinalframework.data.annotation.Json;
import org.ifinalframework.data.mapping.Property;
import org.ifinalframework.data.query.type.JsonParameterTypeHandler;

import org.apache.ibatis.type.TypeHandler;

/**
 * Utils.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public final class TypeHandlers {

    private TypeHandlers() {
    }

    public static Class<? extends TypeHandler<?>> findTypeHandler(final Property property) {
        if (property.isAnnotationPresent(org.ifinalframework.data.annotation.TypeHandler.class)) {
            return property.getRequiredAnnotation(org.ifinalframework.data.annotation.TypeHandler.class).value();
        }

        if (property.isAnnotationPresent(Json.class)) {
            return JsonParameterTypeHandler.class;
        }

        if (property.isCollectionLike()) {
            return JsonParameterTypeHandler.class;
        }

        return null;

    }

}
