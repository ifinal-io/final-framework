package org.ifinal.finalframework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.ifinal.finalframework.web.annotation.servlet.Interceptor;
import org.ifinal.finalframework.core.generator.TraceGenerator;
import org.ifinal.finalframework.core.generator.UuidTraceGenerator;
import org.ifinal.finalframework.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

/**
 * @author likly
 * @version 1.0.0
 * @see TraceGenerator
 * @see UuidTraceGenerator
 * @since 1.0.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Interceptor
public class TraceHandlerInterceptor implements AsyncHandlerInterceptor {

    public static final String TRACE_ATTRIBUTE = "org.ifinal.finalframework.handler.trace";

    private static final Logger logger = LoggerFactory.getLogger(TraceHandlerInterceptor.class);

    private static final String TRACE = "trace";

    /**
     * TRACE 名称，放置到{@link MDC}中的KEY的名称
     */
    @Setter
    private String traceName = TRACE;

    /**
     * TRACE 参数名称
     */
    @Setter
    private String paramName = TRACE;

    /**
     * TRACE 请求头名称
     */
    @Setter
    private String headerName = TRACE;

    @Setter
    private TraceGenerator generator = new UuidTraceGenerator();

    @Override
    public boolean preHandle(final @NonNull HttpServletRequest request, final @NonNull HttpServletResponse response,
        final @NonNull Object handler) {
        // 获取 header 中是否有自定义的 trace
        String trace = null;
        if (Asserts.nonEmpty(paramName)) {
            trace = request.getParameter(paramName);
        }
        if (Asserts.isNull(trace) && Asserts.nonEmpty(headerName)) {
            trace = request.getHeader(headerName);

        }
        if (Asserts.isNull(trace)) {
            // 如果 header 中没有 trace，则获取 Request 域中的 trace 属性
            trace = (String) request.getAttribute(TRACE_ATTRIBUTE);
        }
        if (trace == null && generator != null) {
            // 如果 trace 还为空，则生成一个新的 trace
            trace = generator.generate();
        }
        request.setAttribute(TRACE_ATTRIBUTE, trace);
        MDC.put(traceName, trace);
        return true;
    }

    @Override
    public void afterConcurrentHandlingStarted(final @NonNull HttpServletRequest request,
        final @NonNull HttpServletResponse response,
        final @NonNull Object handler) {

        logger.info("remove trace from MDC context");
        MDC.remove(traceName);
    }

}
