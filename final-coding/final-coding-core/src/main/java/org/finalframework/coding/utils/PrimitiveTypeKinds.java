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

package org.finalframework.coding.utils;


import org.springframework.lang.NonNull;

import javax.lang.model.type.TypeKind;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-13 21:04:51
 * @since 1.0
 */
public abstract class PrimitiveTypeKinds {

    private static final Map<TypeKind, Class<?>> PRIMITIVE_TYPES = new HashMap<>();

    static {
        PRIMITIVE_TYPES.put(TypeKind.BYTE, Byte.class);
        PRIMITIVE_TYPES.put(TypeKind.SHORT, Short.class);

        PRIMITIVE_TYPES.put(TypeKind.CHAR, Character.class);
        PRIMITIVE_TYPES.put(TypeKind.BOOLEAN, Boolean.class);

        PRIMITIVE_TYPES.put(TypeKind.INT, Integer.class);
        PRIMITIVE_TYPES.put(TypeKind.LONG, Long.class);

        PRIMITIVE_TYPES.put(TypeKind.FLOAT, Float.class);
        PRIMITIVE_TYPES.put(TypeKind.DOUBLE, Double.class);
    }

    public static Class<?> getPrimitiveTypeKind(@NonNull TypeKind kind) {
        return PRIMITIVE_TYPES.get(kind);
    }
}

