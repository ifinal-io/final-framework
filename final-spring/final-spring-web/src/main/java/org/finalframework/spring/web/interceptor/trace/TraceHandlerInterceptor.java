package org.finalframework.spring.web.interceptor.trace;

import org.finalframework.core.Assert;
import org.finalframework.core.generator.TraceGenerator;
import org.finalframework.core.generator.UUIDTraceGenerator;
import org.finalframework.spring.annotation.factory.SpringHandlerInterceptor;
import org.finalframework.spring.web.interceptor.HandlerInterceptorWebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-28 15:18
 * @see TraceGenerator
 * @see UUIDTraceGenerator
 * @see HandlerInterceptorWebMvcConfigurer
 * @since 1.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@SpringHandlerInterceptor
@EnableConfigurationProperties(TraceHandlerInterceptorProperties.class)
public class TraceHandlerInterceptor implements AsyncHandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(TraceHandlerInterceptor.class);

    public static final String TRACE_ATTRIBUTE = "org.finalframework.handler.trace";

    private final TraceHandlerInterceptorProperties properties;

    public TraceHandlerInterceptor(TraceHandlerInterceptorProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取 header 中是否有自定义的 trace
        String trace = null;
        if (Assert.nonEmpty(properties.getParamName())) {
            trace = request.getParameter(properties.getParamName());
        }
        if (Assert.isNull(trace) && Assert.nonEmpty(properties.getHeaderName())) {
            trace = request.getHeader(properties.getHeaderName());

        }
        if (Assert.isNull(trace)) {
            // 如果 header 中没有 trace，则获取 Request 域中的 trace 属性
            trace = (String) request.getAttribute(TRACE_ATTRIBUTE);
        }
        if (trace == null) {
            // 如果 trace 还为空，则生成一个新的 trace
            trace = properties.getGenerator().generate();
        }
        request.setAttribute(TRACE_ATTRIBUTE, trace);
        MDC.put(properties.getTraceName(), trace);
        logger.info("put trace to MDC context: {}", trace);
        return true;
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("remove trace from MDC context");
        MDC.remove(properties.getTraceName());
    }
}
