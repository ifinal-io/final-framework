package org.ifinal.finalframework.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.ifinal.finalframework.json.context.JsonContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */

@Intercepts(
        {
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
        }
)
@Component
@SuppressWarnings("unused")
public class JsonIgnoreInterceptor implements Interceptor {

    @Override
    public Object intercept(final Invocation invocation) throws Throwable {

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
    public Object plugin(final Object target) {

        return Plugin.wrap(target, this);
    }

}
