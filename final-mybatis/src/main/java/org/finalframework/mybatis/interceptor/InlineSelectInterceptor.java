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

package org.finalframework.mybatis.interceptor;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.annotation.query.Pageable;
import org.finalframework.data.query.Query;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.finalframework.mybatis.resumtmap.ResultMapFactory;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.springframework.core.annotation.Order;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 分页拦截器
 *
 * <ul>
 * <li>单一参数实现了 {@link Pageable} 接口</li>
 * <li>参数列表中有一个参数实现了 {@link Pageable} 接口</li>
 * </ul>
 *
 * @author likly
 * @version 1.0
 * @date 2019-01-15 21:58:13
 * @since 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
@Order(0)
@SpringComponent
public class InlineSelectInterceptor implements Interceptor {

    /**
     * @see MapperBuilderAssistant#getStatementResultMaps(java.lang.String, java.lang.Class, java.lang.String)
     * @see AbsMapper#select(String, Class, Collection, Query)
     * @see AbsMapper#selectOne(String, Class, Serializable, Query)
     */
    private static final Pattern PATTERN = Pattern.compile("(\\.select|\\.selectOne|-Inline)$");


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        try {
            Executor executor = (Executor) invocation.getTarget();
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            Object param = args[1];
            RowBounds rowBounds = (RowBounds) args[2];
            ResultHandler resultHandler = (ResultHandler) args[3];
            final String id = ms.getId();
            final String methodName = id.substring(id.lastIndexOf(".") + 1);

            final List<ResultMap> resultMaps = ms.getResultMaps();

            if (resultMaps.size() == 1 && PATTERN.matcher(id).find()
                    && IEntity.class.isAssignableFrom(resultMaps.get(0).getType())) {
                final MappedStatement mappedStatement = newCountMappedStatement(ms, id + "-final");
                return executor.query(mappedStatement, param, rowBounds, resultHandler);
            }


            return invocation.proceed();

        } catch (Exception e) {
            return invocation.proceed();
        }

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public MappedStatement newCountMappedStatement(MappedStatement ms, String newMsId) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), newMsId, ms.getSqlSource(), ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        //count查询返回值int
        List<ResultMap> resultMaps = new ArrayList<>();
        final ResultMap resultMap = ResultMapFactory.from(ms.getConfiguration(), ms.getResultMaps().get(0).getType());
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }
}
