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

import org.ifinalframework.core.Pageable;
import org.ifinalframework.data.query.PageQuery;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;

/**
 * 分页拦截器
 *
 * <ul>
 * <li>单一参数实现了 {@link Pageable} 接口</li>
 * <li>参数列表中有一个参数实现了 {@link Pageable} 接口</li>
 * </ul>
 *
 * @author iimik
 * @version 1.0.0
 * @see Pageable
 * @since 1.0.0
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class,
                CacheKey.class,
                BoundSql.class}),
})
public abstract class PageableInterceptor implements Interceptor {

    private static final String PAGE_PARAMETER = "page";

    private static final String SIZE_PARAMETER = "size";

    private static final String COUNT_PARAMETER = "count";

    @Override
    @SuppressWarnings("unchecked")
    public Object intercept(final Invocation invocation) throws Throwable {

        try {
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            Object parameter = args[1];
            if (!ms.getId().contains("selectCount") && !ms.getId().contains("selectOne") && parameter != null) {
                if (parameter instanceof Map) {
                    startPage(findPage((Map<String, Object>) parameter));
                    return invocation.proceed();

                } else if (parameter instanceof Pageable) {
                    startPage((Pageable) parameter);
                    return invocation.proceed();
                }
            }
            return invocation.proceed();

        } catch (Exception e) {
            return invocation.proceed();
        }

    }

    private Pageable findPage(final Map<String, Object> map) {

        for (Object item : map.values()) {
            if (item instanceof Pageable) {
                return (Pageable) item;
            }
        }

        PageQuery query = new PageQuery();

        query.setPage(null);
        query.setSize(null);
        query.setCount(null);

        if (map.containsKey(PAGE_PARAMETER) && map.get(PAGE_PARAMETER) instanceof Integer) {
            query.setPage((Integer) map.get(PAGE_PARAMETER));
        }
        if (map.containsKey(SIZE_PARAMETER) && map.get(SIZE_PARAMETER) instanceof Integer) {
            query.setSize((Integer) map.get(SIZE_PARAMETER));
        }
        if (map.containsKey(COUNT_PARAMETER) && map.get(COUNT_PARAMETER) instanceof Boolean) {
            query.setCount((Boolean) map.get(COUNT_PARAMETER));
        }

        return query;

    }

    protected abstract void startPage(Pageable pageable);


}
