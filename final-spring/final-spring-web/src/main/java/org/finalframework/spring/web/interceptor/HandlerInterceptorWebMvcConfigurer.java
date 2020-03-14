package org.finalframework.spring.web.interceptor;

import org.finalframework.core.Assert;
import org.finalframework.data.util.Beans;
import org.finalframework.spring.annotation.factory.SpringHandlerInterceptor;
import org.finalframework.spring.annotation.factory.SpringWebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-28 15:23
 * @since 1.0
 */
@SpringWebMvcConfigurer
public class HandlerInterceptorWebMvcConfigurer implements WebMvcConfigurer, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptorWebMvcConfigurer.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Beans.findBeansByAnnotation(applicationContext, SpringHandlerInterceptor.class)
                .forEach(item -> {
                    SpringHandlerInterceptor annotation = item.getClass().getAnnotation(SpringHandlerInterceptor.class);
                    InterceptorRegistration interceptorRegistration = registry.addInterceptor((org.springframework.web.servlet.HandlerInterceptor) item);
                    if (item instanceof IHandlerInterceptor) {
                        IHandlerInterceptor handlerInterceptor = (IHandlerInterceptor) item;
                        if (Assert.nonEmpty(handlerInterceptor.getPathPatterns())) {
                            interceptorRegistration.addPathPatterns(handlerInterceptor.getPathPatterns());
                        }
                        if (Assert.nonEmpty(handlerInterceptor.getExcludePathPatterns())) {
                            interceptorRegistration.excludePathPatterns(handlerInterceptor.getExcludePathPatterns());
                        }
                    } else {
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

                    logger.info("==> add interceptor={},includes={},excludes={},point={}",
                            item.getClass(),
                            annotation.includes(),
                            annotation.excludes(),
                            order == null ? 0 : order.value()
                    );
                });


    }

}
