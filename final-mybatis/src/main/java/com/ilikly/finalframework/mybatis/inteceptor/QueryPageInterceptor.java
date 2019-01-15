package com.ilikly.finalframework.mybatis.inteceptor;

import com.github.pagehelper.PageHelper;
import com.ilikly.finalframework.data.query.Query;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;

/**
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
public class QueryPageInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(QueryPageInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        try {
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            Object parameter = args[1];
            if (!ms.getId().endsWith(".selectCount") && parameter != null) {
                if (parameter instanceof Map) {
                    Map map = (Map) parameter;
                    for (Object item : map.values()) {
                        if (item instanceof Query) {
                            startPage((Query) item);
                        }
                    }
                } else if (parameter instanceof Query) {
                    startPage((Query) parameter);
                }
            }
            return invocation.proceed();

        } catch (Exception e) {
            return invocation.proceed();
        }

    }

    private void startPage(Query query) {
        logger.info("startPage:page={},size={}", query.getPage(), query.getSize());
        if (query.getPage() != null && query.getSize() != null) {
            PageHelper.startPage(query.getPage(), query.getSize());
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
