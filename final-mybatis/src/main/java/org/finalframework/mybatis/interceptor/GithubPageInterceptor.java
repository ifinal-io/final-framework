package org.finalframework.mybatis.interceptor;


import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-27 15:13:58
 * @since 1.0
 */
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
@Order(Ordered.HIGHEST_PRECEDENCE)
@SpringComponent
public class GithubPageInterceptor extends PageInterceptor {
}

