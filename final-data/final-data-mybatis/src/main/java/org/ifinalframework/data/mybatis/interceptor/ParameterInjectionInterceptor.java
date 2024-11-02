/*
 * Copyright 2020-2021 the original author or authors.
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

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import org.ifinalframework.data.mybatis.mapper.AbsMapper;
import org.ifinalframework.data.query.DefaultQEntityFactory;
import org.ifinalframework.data.query.QEntity;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;

/**
 * 参数注入拦截器
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class,
                BoundSql.class}),
})
@Order
@Component
@SuppressWarnings({"unchecked"})
public class ParameterInjectionInterceptor extends AbsMapperInterceptor {

    private static final String PROPERTIES_PARAMETER_NAME = "properties";

    @Override
    protected Object intercept(Invocation invocation, Class<?> mapper, Class<?> entityClass) throws Throwable {

        Object[] args = invocation.getArgs();

        Object parameter = args[1];

        if (parameter instanceof Map && AbsMapper.class.isAssignableFrom(mapper)) {
            Map<String, Object> parameters = (Map<String, Object>) parameter;


            final QEntity<?, ?> entity = DefaultQEntityFactory.INSTANCE.create(entityClass);
            parameters.putIfAbsent(PROPERTIES_PARAMETER_NAME, entity);


        }

        return invocation.proceed();


    }


}
