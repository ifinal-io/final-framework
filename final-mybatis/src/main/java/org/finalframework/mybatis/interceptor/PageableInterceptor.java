

package org.finalframework.mybatis.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.finalframework.annotation.Pageable;
import org.finalframework.data.query.PageQuery;

import java.util.Map;
import java.util.Properties;

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

public abstract class PageableInterceptor implements Interceptor {

    private static final String PAGE_PARAMETER = "page";
    private static final String SIZE_PARAMETER = "size";
    private static final String COUNT_PARAMETER = "count";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        try {
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            Object parameter = args[1];
            if (!ms.getId().contains("selectCount") && !ms.getId().contains("selectOne") && parameter != null) {
                if (parameter instanceof Map) {
                    Map map = (Map) parameter;
                    for (Object item : map.values()) {
                        if (item instanceof Pageable) {
                            startPage((Pageable) item);
                            return invocation.proceed();
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
                    startPage(query);
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

    protected abstract void startPage(Pageable pageable);

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
