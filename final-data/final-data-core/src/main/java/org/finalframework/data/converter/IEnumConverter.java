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

package org.finalframework.data.converter;

import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.util.Enums;
import org.finalframework.data.util.Messages;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-08 14:34:47
 * @since 1.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnumTarget(Enum.class)
@SpringComponent
public class IEnumConverter<T extends Enum<T>> implements EnumConverter<T> {

    /**
     * @see Enum#ordinal()
     */
    protected static final String ENUM_ORDINAL = "ordinal";
    /**
     * @see Enum#name()
     */
    protected static final String ENUM_NAME = "name";
    /**
     * @see IEnum#getCode()
     */
    protected static final String ENUM_CODE = "code";
    /**
     * @see IEnum#getDesc()
     */
    protected static final String ENUM_DESC = "desc";

    @Override
    public Map<String, Object> convert(T source) {
        final Map<String, Object> result = new HashMap<>();
        result.put(ENUM_NAME, source.name());
        result.put(ENUM_ORDINAL, source.ordinal());

        if (source instanceof IEnum) {
            result.put(ENUM_CODE, ((IEnum<?>) source).getCode());
            result.put(ENUM_DESC, Messages.getMessage(Enums.getEnumI18NCode(source), ((IEnum<?>) source).getDesc()));
        }
        return result;
    }
}
