package org.ifinal.finalframework.web.autoconfiguration;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.annotation.web.servlet.Interceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author likly
 * @version 1.0.0
 * @see HandlerInterceptor
 * @since 1.0.0
 */
@Slf4j
@Component
@SuppressWarnings("unused")
public class HandlerInterceptorAutoConfiguration implements WebMvcConfigurer {

    private final List<HandlerInterceptor> handlerInterceptors;

    public HandlerInterceptorAutoConfiguration(final ObjectProvider<List<HandlerInterceptor>> handlerInterceptorsObjectProvider) {
        this.handlerInterceptors = handlerInterceptorsObjectProvider.getIfAvailable();
    }

    @Override
    public void addInterceptors(final @NonNull InterceptorRegistry registry) {
        handlerInterceptors.stream()
            // filter the interceptor annotated by @Component
            .filter(it -> AnnotatedElementUtils.isAnnotated(it.getClass(), Component.class))
            .forEach(item -> this.addInterceptor(registry, item));

    }

    private void addInterceptor(final @NonNull InterceptorRegistry registry, final @NonNull HandlerInterceptor interceptor) {

        final Interceptor annotation = AnnotationUtils.getAnnotation(interceptor.getClass(), Interceptor.class);
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(interceptor);
        if (annotation != null) {
            if (annotation.includes().length > 0) {
                interceptorRegistration.addPathPatterns(annotation.includes());
            }
            if (annotation.excludes().length > 0) {
                interceptorRegistration.excludePathPatterns(annotation.excludes());
            }
        }
        final Order order = AnnotationUtils.getAnnotation(interceptor.getClass(), Order.class);
        if (order != null) {
            interceptorRegistration.order(order.value());
        }
        logger.info("==> add interceptor={}", interceptor.getClass());
    }

}
