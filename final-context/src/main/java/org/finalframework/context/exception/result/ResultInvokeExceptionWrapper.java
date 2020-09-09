

package org.finalframework.context.exception.result;

import org.finalframework.annotation.result.R;
import org.finalframework.annotation.result.Result;
import org.finalframework.context.exception.InvokeExceptionWrapper;
import org.finalframework.context.exception.handler.GlobalExceptionHandler;
import org.finalframework.core.Asserts;
import org.slf4j.MDC;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author likly
 * @version 1.0
 * @date 2019-04-15 14:00:27
 * @since 1.0
 */
public abstract class ResultInvokeExceptionWrapper<T extends Serializable> implements InvokeExceptionWrapper<Result<T>> {

    private GlobalExceptionHandler<Result<?>> globalExceptionHandler;

    public ResultInvokeExceptionWrapper(GlobalExceptionHandler<Result<?>> globalExceptionHandler) {
        this.globalExceptionHandler = globalExceptionHandler;
    }

    public GlobalExceptionHandler<Result<?>> getGlobalExceptionHandler() {
        return globalExceptionHandler;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Result<T> invoke() {
        String trace = MDC.get("trace");
        if (Asserts.isBlank(trace)) {
            trace = UUID.randomUUID().toString();
            MDC.put("trace", trace);
        }
        InvokeContext context = new InvokeContext();
        context.setTrace(trace);
        try {
            final T data = handle();
            final Result<T> result = R.success(data);
            result.setTrace(trace);
            result.setTimestamp(System.currentTimeMillis());
            return result;
        } catch (Throwable throwable) {
            if (globalExceptionHandler != null) {
                return (Result<T>) globalExceptionHandler.handle(throwable);
            }
            final Result<?> result = R.failure(500, "服务异常");
            result.setTrace(trace);
            result.setTimestamp(System.currentTimeMillis());
            return (Result<T>) result;
        }
    }

    protected abstract T handle() throws Throwable;

}
