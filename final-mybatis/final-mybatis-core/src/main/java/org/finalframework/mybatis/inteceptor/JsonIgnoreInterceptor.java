package org.finalframework.mybatis.inteceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.finalframework.json.context.JsonContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 21:58:13
 * @since 1.0
 */
@SuppressWarnings({"rawtypes"})
@Intercepts(
    {
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
    }
)
public class JsonIgnoreInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(JsonIgnoreInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            JsonContextHolder.setIgnore(true, true);
            return invocation.proceed();
        } finally {
            JsonContextHolder.setIgnore(false, false);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}
