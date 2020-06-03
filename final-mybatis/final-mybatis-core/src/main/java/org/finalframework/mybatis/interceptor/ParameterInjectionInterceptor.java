package org.finalframework.mybatis.interceptor;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.finalframework.data.annotation.Final;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.repository.Repository;
import org.finalframework.data.repository.RepositoryHolder;
import org.finalframework.data.repository.RepositoryManager;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Map;
import java.util.Properties;

/**
 * 参数注入拦截器
 *
 * @author likly
 * @version 1.0
 * @date 2020-04-19 21:58:13
 * @since 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Intercepts(
        {
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
@Order
@SpringComponent
public class ParameterInjectionInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(ParameterInjectionInterceptor.class);
    /**
     * @see MapperBuilderAssistant#getStatementResultMaps(String, Class, String)
     */
    private static final String INLINE_RESULT_MAP_SUFFIX = "-Inline";

    private static final String TABLE_PARAMETER_NAME = "table";
    private static final String PROPERTIES_PARAMETER_NAME = "properties";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        final String id = ms.getId();
        logger.debug("Inject Parameter start...:{}", id);
        try {

            final String mapperName = id.substring(0, id.lastIndexOf("."));
            final Class<?> mapper = Class.forName(mapperName);
            Object parameter = args[1];

            if (parameter instanceof Map && Repository.class.isAssignableFrom(mapper)) {
                Map<String, Object> parameters = (Map<String, Object>) parameter;
                final RepositoryHolder holder = RepositoryManager.from((Class<? extends Repository>) mapper);
                if (holder != null) {
//                    Entity.from(holder.getEntity())
                    final QEntity<?, ?> entity = QEntity.from(holder.getEntity());
                    parameters.computeIfAbsent(TABLE_PARAMETER_NAME, k -> entity.getTable());
                    parameters.putIfAbsent(PROPERTIES_PARAMETER_NAME, entity);

                }
            }


            return invocation.proceed();

        } catch (Exception e) {
            return invocation.proceed();
        } finally {
            logger.debug("Inject Parameter Finish...:{}", id);
        }

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
