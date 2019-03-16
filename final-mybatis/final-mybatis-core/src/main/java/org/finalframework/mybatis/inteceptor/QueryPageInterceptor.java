package org.finalframework.mybatis.inteceptor;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.finalframework.core.Assert;
import org.finalframework.data.query.Pageable;
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
            if (!ms.getId().endsWith("selectCount") && !ms.getId().endsWith("selectOne") && parameter != null) {
                if (parameter instanceof Map) {
                    Map map = (Map) parameter;
                    for (Object item : map.values()) {
                        if (item instanceof Pageable) {
                            startPage((Pageable) item);
                            return invocation.proceed();
                        }
                    }
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

    private void startPage(Pageable pageable) {
        if (Assert.isNull(pageable)) return;
        startPage(pageable.getPage(), pageable.getSize());
    }

    private void startPage(Integer page, Integer size) {
        logger.info("startPage:page={},size={}", page, size);
        if (page != null && size != null) {
            final Page<Object> result = PageHelper.startPage(page, size);
            logger.info("pageResult:page={},size={},pages={},total={}",
                    result.getPageNum(), result.getPageSize(), result.getPages(), result.getTotal());
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
