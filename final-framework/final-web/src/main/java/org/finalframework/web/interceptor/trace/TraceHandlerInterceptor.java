package org.finalframework.web.interceptor.trace;

import lombok.Setter;
import org.finalframework.web.annotation.HandlerInterceptor;
import org.finalframework.core.generator.TraceGenerator;
import org.finalframework.core.generator.UUIDTraceGenerator;
import org.finalframework.util.Asserts;
import org.finalframework.web.interceptor.AbsHandlerInterceptor;
import org.finalframework.web.interceptor.TraceHandlerInterceptorProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-28 15:18
 * @see TraceGenerator
 * @see UUIDTraceGenerator
 * @since 1.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@HandlerInterceptor
@EnableConfigurationProperties(TraceHandlerInterceptorProperties.class)
public class TraceHandlerInterceptor extends AbsHandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(TraceHandlerInterceptor.class);

    public static final String TRACE_ATTRIBUTE = "org.finalframework.handler.trace";

    /**
     * TRACE 名称，放置到{@link MDC}中的KEY的名称
     */
    @Setter
    private String traceName;
    /**
     * TRACE 参数名称
     */
    @Setter
    private String paramName;
    /**
     * TRACE 请求头名称
     */
    @Setter
    private String headerName;

    @Setter
    private TraceGenerator generator;

    @Autowired
    public TraceHandlerInterceptor(TraceHandlerInterceptorProperties properties) {
        super(properties);
        this.setTraceName(properties.getTraceName());
        this.setParamName(properties.getParamName());
        this.setHeaderName(properties.getHeaderName());
        try {
            this.setGenerator(properties.getGenerator().getConstructor().newInstance());
        } catch (Exception e) {
            //ignore
        }
    }

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
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
        logger.info("put trace to MDC context: {}", trace);
        return true;
    }

    @Override
    public void afterConcurrentHandlingStarted(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        logger.info("remove trace from MDC context");
        MDC.remove(traceName);
    }
}
