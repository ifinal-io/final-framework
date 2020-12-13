package org.ifinal.finalframework.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ifinal.finalframework.web.annotation.HandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@HandlerInterceptor
public class DurationHandlerInterceptor implements AsyncHandlerInterceptor {

    public static final String DURATION_START_ATTRIBUTE = "org.ifinal.finalframework.handler.duration.start";

    public static final String DURATION_END_ATTRIBUTE = "org.ifinal.finalframework.handler.duration.end";

    public static final String DURATION_ATTRIBUTE = "org.ifinal.finalframework.handler.duration";

    private static final Logger logger = LoggerFactory.getLogger(DurationHandlerInterceptor.class);

    @Override
    public boolean preHandle(final @NonNull HttpServletRequest request, final @NonNull HttpServletResponse response, final @NonNull Object handler) {

        String uri = request.getRequestURI();
        long durationStart = System.currentTimeMillis();
        request.setAttribute(DURATION_START_ATTRIBUTE, durationStart);
        if (logger.isDebugEnabled()) {
            logger.debug("METHOD={},URI={},START={}", request.getMethod(), uri, durationStart);
        }

        return true;
    }

    @Override
    public void postHandle(final @NonNull HttpServletRequest request, final @NonNull HttpServletResponse response, final @NonNull Object handler,
        final ModelAndView modelAndView) {

        Long durationStart = (Long) request.getAttribute(DURATION_START_ATTRIBUTE);
        String uri = request.getRequestURI();
        long durationEnd = System.currentTimeMillis();
        long duration = durationEnd - durationStart;
        request.setAttribute(DURATION_END_ATTRIBUTE, durationEnd);
        request.setAttribute(DURATION_ATTRIBUTE, duration);
        if (logger.isDebugEnabled()) {
            logger.debug("METHOD={},URI={},START={},DURATION={}", request.getMethod(), uri, durationEnd, duration);
        }

    }

}

