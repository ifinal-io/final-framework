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

package org.ifinalframework.data.query.type;

import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import org.ifinalframework.json.Json;

import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Converter the parameter to a {@code json} String.
 *
 * @author iimik
 * @version 1.0.0
 * @see org.ifinalframework.data.annotation.Json
 * @see java.util.Collection
 * @see java.util.Map
 * @see java.util.List
 * @see java.util.Set
 * @since 1.0.0
 */
public class JsonParameterTypeHandler extends ParameterTypeHandler<Object> {

    private static final boolean H2_IS_PRESENT = ClassUtils.isPresent("org.h2.api.H2Type", JsonParameterTypeHandler.class.getClassLoader());

    private static Class<?> VALUE_JSON_CLASS;
    private static Method FROM_JSON_METHOD;

    static {
        try {
            if (H2_IS_PRESENT) {
                ClassLoader classLoader = JsonParameterTypeHandler.class.getClassLoader();
                VALUE_JSON_CLASS = ClassUtils.forName("org.h2.value.ValueJson", classLoader);
                FROM_JSON_METHOD = ReflectionUtils.findMethod(VALUE_JSON_CLASS, "fromJson", String.class);
            }
        } catch (Exception e) {
            // ignore
        }
    }


    @Override
    public void setNonNullParameter(final PreparedStatement ps, final int i, final Object parameter,
                                    final JdbcType jdbcType) throws SQLException {
        if (H2_IS_PRESENT) {
            try {
                Object value = FROM_JSON_METHOD.invoke(VALUE_JSON_CLASS, Json.toJson(parameter));
                ps.setObject(i, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            ps.setString(i, Json.toJson(parameter));
        }
    }

}

