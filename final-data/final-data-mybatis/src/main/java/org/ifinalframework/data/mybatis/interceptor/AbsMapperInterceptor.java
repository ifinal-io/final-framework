/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.data.mybatis.interceptor;

import org.springframework.core.ResolvableType;
import org.springframework.lang.NonNull;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.data.mybatis.mapper.AbsMapper;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.io.Serializable;

import lombok.extern.slf4j.Slf4j;

/**
 * AbsMapperInterceptor.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 */
@Slf4j
public abstract class AbsMapperInterceptor implements Interceptor {

    private static <I extends Serializable, T extends IEntity<I>> Class<T> from(
            final @NonNull Class<? extends AbsMapper> mapper) {
        return (Class<T>) ResolvableType.forClass(mapper)
                .as(AbsMapper.class)
                .resolveGeneric(1);

    }

    @Override
    public final Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        final String id = ms.getId();

        final String mapperName = id.substring(0, id.lastIndexOf("."));
        final Class<?> mapper = Class.forName(mapperName);

        if (AbsMapper.class.isAssignableFrom(mapper)) {
            return intercept(invocation, mapper, from((Class<? extends AbsMapper>) mapper));
        } else {
            return invocation.proceed();
        }


    }

    protected abstract Object intercept(Invocation invocation, Class<?> mapper, Class<?> entity) throws Throwable;
}
