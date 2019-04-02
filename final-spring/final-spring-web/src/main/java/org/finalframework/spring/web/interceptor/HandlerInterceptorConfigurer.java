package org.finalframework.spring.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.spring.util.BeanUtils;
import org.finalframework.spring.web.interceptor.annotation.HandlerInterceptor;
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
@Slf4j
public class HandlerInterceptorConfigurer implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        BeanUtils.findBeansByAnnotation(applicationContext, HandlerInterceptor.class).forEach(item -> {
            HandlerInterceptor annotation = item.getClass().getAnnotation(HandlerInterceptor.class);
            InterceptorRegistration interceptorRegistration = registry.addInterceptor((org.springframework.web.servlet.HandlerInterceptor) item);
            if (annotation.includes().length > 0) {
                interceptorRegistration.addPathPatterns(annotation.includes());
            }
            if (annotation.excludes().length > 0) {
                interceptorRegistration.excludePathPatterns(annotation.excludes());
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
