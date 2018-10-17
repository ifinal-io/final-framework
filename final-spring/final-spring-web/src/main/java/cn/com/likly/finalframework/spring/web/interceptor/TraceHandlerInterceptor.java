package cn.com.likly.finalframework.spring.web.interceptor;

import cn.com.likly.finalframework.spring.web.interceptor.annotation.HandlerInterceptor;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-28 15:18
 * @since 1.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@HandlerInterceptor
public class TraceHandlerInterceptor implements AsyncHandlerInterceptor {

    private static final String TRACE = "trace";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String trace = request.getHeader(TRACE);
        if (trace == null) {
            trace = UUID.randomUUID().toString();
        }

        MDC.put(TRACE, trace);

        return true;
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.clear();
    }
}
