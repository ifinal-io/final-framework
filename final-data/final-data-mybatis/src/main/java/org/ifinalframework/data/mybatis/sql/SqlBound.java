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

package org.ifinalframework.data.mybatis.sql;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IQuery;

import org.apache.ibatis.type.TypeHandler;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * SqlBound.
 *
 * @author iimik
 * @version 1.0.0
 * @see org.apache.ibatis.mapping.BoundSql
 * @since 1.0.0
 */
@Data
public class SqlBound implements Serializable {

    private static final long serialVersionUID = 641816143590205180L;

    private Class<? extends IEntity<?>> entity;

    private Class<? extends IQuery> query;

    private String script;

    private String sql;

    private List<ParameterMapping> parameterMappings;

    /**
     * ParameterMapping.
     */
    @Data
    public static class ParameterMapping implements Serializable {

        private static final long serialVersionUID = 8682535333697518308L;

        private String property;

        private String expression;

        private Class<?> javaType;

        private Class<? extends TypeHandler<?>> typeHandler;

        private Object value;

    }

}
