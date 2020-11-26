package org.ifinal.finalframework.mybatis.interceptor;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.ifinal.finalframework.annotation.IEntity;
import org.ifinal.finalframework.data.query.QEntity;
import org.ifinal.finalframework.data.repository.Repository;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Properties;

/**
 * 参数注入拦截器
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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
@Component
public class ParameterInjectionInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(ParameterInjectionInterceptor.class);
    /**
     * @see MapperBuilderAssistant#getStatementResultMaps(String, Class, String)
     */
    private static final String INLINE_RESULT_MAP_SUFFIX = "-Inline";

    private static final String TABLE_PARAMETER_NAME = "table";
    private static final String PROPERTIES_PARAMETER_NAME = "properties";

    public static <ID extends Serializable, T extends IEntity<ID>> Class<T> from(@NonNull Class<? extends AbsMapper> mapper) {

        Type[] genericInterfaces = mapper.getGenericInterfaces();
        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType && Repository.class
                    .isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
                Class<ID> id = (Class<ID>) ((ParameterizedType) type).getActualTypeArguments()[0];
                Class<T> entity = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[1];
                return entity;

            }
        }
        return null;
    }

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

            if (parameter instanceof Map && AbsMapper.class.isAssignableFrom(mapper)) {
                Map<String, Object> parameters = (Map<String, Object>) parameter;

                final Class<IEntity<Serializable>> entityClass = from((Class<? extends AbsMapper>) mapper);
//                final RepositoryHolder holder = RepositoryManager.from((Class<? extends Repository>) mapper);
//                if (holder != null) {
//                    Entity.from(holder.getEntity())
                final QEntity<?, ?> entity = QEntity.from(entityClass);
                parameters.computeIfAbsent(TABLE_PARAMETER_NAME, k -> entity.getTable());
                parameters.putIfAbsent(PROPERTIES_PARAMETER_NAME, entity);

//                }
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
