package org.finalframework.spring.web.interceptor;

import org.finalframework.core.Assert;
import org.finalframework.spring.annotation.factory.SpringHandlerInterceptor;
import org.finalframework.spring.annotation.factory.SpringWebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-28 15:23
 * @since 1.0
 */
@SpringWebMvcConfigurer
public class HandlerInterceptorWebMvcConfigurer implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptorWebMvcConfigurer.class);

    private final List<HandlerInterceptor> handlerInterceptors;

    public HandlerInterceptorWebMvcConfigurer(ObjectProvider<List<HandlerInterceptor>> handlerInterceptorsObjectProvider) {
        this.handlerInterceptors = handlerInterceptorsObjectProvider.getIfAvailable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        handlerInterceptors.forEach(item -> {
            SpringHandlerInterceptor annotation = item.getClass().getAnnotation(SpringHandlerInterceptor.class);
            InterceptorRegistration interceptorRegistration = registry.addInterceptor(item);
            if (item instanceof IHandlerInterceptor) {
                IHandlerInterceptor handlerInterceptor = (IHandlerInterceptor) item;
                if (Assert.nonEmpty(handlerInterceptor.getPathPatterns())) {
                    interceptorRegistration.addPathPatterns(handlerInterceptor.getPathPatterns());
                }
                if (Assert.nonEmpty(handlerInterceptor.getExcludePathPatterns())) {
                    interceptorRegistration.excludePathPatterns(handlerInterceptor.getExcludePathPatterns());
                }
                interceptorRegistration.order(handlerInterceptor.getOrder());


            } else if (annotation != null) {
                if (annotation.includes().length > 0) {
                    interceptorRegistration.addPathPatterns(annotation.includes());
                }
                if (annotation.excludes().length > 0) {
                    interceptorRegistration.excludePathPatterns(annotation.excludes());
                }
            }


            Order order = item.getClass().getAnnotation(Order.class);
            if (order != null) {
                interceptorRegistration.order(order.value());
            } else {
                interceptorRegistration.order(Ordered.LOWEST_PRECEDENCE);
            }

            logger.info("==> add interceptor={}", item.getClass());
        });


    }

}
