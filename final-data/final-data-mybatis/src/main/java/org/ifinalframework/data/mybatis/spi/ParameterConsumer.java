/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.mybatis.spi;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;

/**
 * ParameterConsumer.
 *
 * @author iimik
 * @version 1.5.0
 * @see org.apache.ibatis.executor.Executor#query(MappedStatement, Object, RowBounds, ResultHandler, CacheKey, BoundSql)
 * @see org.apache.ibatis.executor.Executor#query(MappedStatement, Object, RowBounds, ResultHandler)
 * @see org.apache.ibatis.executor.Executor#update(MappedStatement, Object)
 * @since 1.5.0
 */
public interface ParameterConsumer<T> {

    boolean supports(Object parameter);

    void accept(T parameter, Class<?> mapper, Method method);
}
