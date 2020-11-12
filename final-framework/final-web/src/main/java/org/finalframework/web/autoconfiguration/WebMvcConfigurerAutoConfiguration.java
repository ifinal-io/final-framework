package org.finalframework.web.autoconfiguration;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.web.annotation.HandlerInterceptor;
import org.finalframework.util.Asserts;
import org.finalframework.web.interceptor.IHandlerInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.ServiceLoader;

/**
 * @author likly
 * @version 1.0
 * @date 2020/10/26 22:37:02
 * @see ConverterFactory
 * @see org.springframework.web.servlet.HandlerInterceptor
 * @since 1.0
 */
@Slf4j
@Component
@SuppressWarnings("rawtypes")
public class WebMvcConfigurerAutoConfiguration implements WebMvcConfigurer {

    private final List<ConverterFactory> converterFactories;
    private final List<org.springframework.web.servlet.HandlerInterceptor> handlerInterceptors;


    public WebMvcConfigurerAutoConfiguration(ObjectProvider<List<ConverterFactory>> converterFactoriesProvider,
                                             ObjectProvider<List<org.springframework.web.servlet.HandlerInterceptor>> handlerInterceptorsObjectProvider) {
        this.converterFactories = converterFactoriesProvider.getIfAvailable();
        this.handlerInterceptors = handlerInterceptorsObjectProvider.getIfAvailable();
    }

    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        logger.info("start register converterFactories ...");
        if (Asserts.nonEmpty(converterFactories)) {
            converterFactories.forEach(registry::addConverterFactory);
        }
        ServiceLoader.load(ConverterFactory.class).forEach(registry::addConverterFactory);
        logger.info("finish register converterFactories ...");
    }


    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        handlerInterceptors.stream()
                // 仅支持通过注解注入的拦截器
                .filter(it -> AnnotatedElementUtils.hasAnnotation(it.getClass(), Component.class))
                .forEach(item -> this.addInterceptor(registry, item));


    }

    private void addInterceptor(@NonNull InterceptorRegistry registry, @NonNull org.springframework.web.servlet.HandlerInterceptor item) {
        HandlerInterceptor annotation = item.getClass().getAnnotation(HandlerInterceptor.class);
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(item);
        if (item instanceof IHandlerInterceptor) {
            IHandlerInterceptor handlerInterceptor = (IHandlerInterceptor) item;
            if (Asserts.nonEmpty(handlerInterceptor.getPathPatterns())) {
                interceptorRegistration.addPathPatterns(handlerInterceptor.getPathPatterns());
            }
            if (Asserts.nonEmpty(handlerInterceptor.getExcludePathPatterns())) {
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
    }
}
