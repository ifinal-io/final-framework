package org.ifinal.finalframework.mybatis.interceptor;

import java.util.Map;
import java.util.Properties;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.ifinal.finalframework.annotation.core.Pageable;
import org.ifinal.finalframework.annotation.query.PageQuery;

/**
 * 分页拦截器
 *
 * <ul>
 * <li>单一参数实现了 {@link Pageable} 接口</li>
 * <li>参数列表中有一个参数实现了 {@link Pageable} 接口</li>
 * </ul>
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Intercepts(
    {
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
            RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
            RowBounds.class, ResultHandler.class,
            CacheKey.class,
            BoundSql.class}),
    }
)

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

    @Override
    public Object plugin(final Object target) {

        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(final Properties properties) {
        //do nothing
    }

}
