package cn.com.likly.finalframework.spring.web.configurer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-28 15:23
 * @since 1.0
 */
@Slf4j
@Configuration
public class HandlerInterceptorConfigurer implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        getHandlerInterceptors(applicationContext).forEach(item -> {

                    cn.com.likly.finalframework.spring.web.interceptor.annotation.HandlerInterceptor annotation = item.getClass().getAnnotation(cn.com.likly.finalframework.spring.web.interceptor.annotation.HandlerInterceptor.class);

                    InterceptorRegistration interceptorRegistration = registry.addInterceptor(item);
                    if (annotation.includes().length > 0) {
                        interceptorRegistration.addPathPatterns(annotation.includes());
                    }
                    if (annotation.excludes().length > 0) {
                        interceptorRegistration.excludePathPatterns(annotation.excludes());
                    }

                    Order order = item.getClass().getAnnotation(Order.class);
                    if (order != null) {
                        interceptorRegistration.order(order.value());
                    }

                    logger.info("==> add interceptor={},includes={},excludes={},order={}",
                            item.getClass(),
                            annotation.includes(),
                            annotation.excludes(),
                            order == null ? 0 : order.value()
                    );
                }
        );
    }

    private List<HandlerInterceptor> getHandlerInterceptors(final ApplicationContext applicationContext) {
        return Arrays.stream(BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, Object.class))
                .filter(name -> applicationContext.findAnnotationOnBean(name, cn.com.likly.finalframework.spring.web.interceptor.annotation.HandlerInterceptor.class) != null)
                .map(name -> (HandlerInterceptor) applicationContext.getBean(name))
                .collect(Collectors.toList());
    }
}
