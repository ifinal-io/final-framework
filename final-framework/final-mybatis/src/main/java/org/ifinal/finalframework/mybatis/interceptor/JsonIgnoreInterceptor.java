package org.ifinal.finalframework.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.ifinal.finalframework.json.context.JsonContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings({"rawtypes"})
@Intercepts(
        {
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
        }
)
@Component
public class JsonIgnoreInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(JsonIgnoreInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        boolean ignoreChanged = false;
        try {
            if (!JsonContextHolder.isIgnore()) {
                JsonContextHolder.setIgnore(true, true);
                ignoreChanged = true;
            }
            return invocation.proceed();
        } finally {
            if (ignoreChanged) {
                JsonContextHolder.setIgnore(false, false);
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}
