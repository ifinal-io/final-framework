package org.ifinal.finalframework.web.autoconfiguration;

import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.util.Asserts;
import org.ifinal.finalframework.web.annotation.HandlerInterceptor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
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
 * @version 1.0.0
 * @see ConverterFactory
 * @see org.springframework.web.servlet.HandlerInterceptor
 * @since 1.0.0
 */
@Slf4j
@Component
@SuppressWarnings({"rawtypes", "unused"})
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

    private void addInterceptor(@NonNull InterceptorRegistry registry, @NonNull org.springframework.web.servlet.HandlerInterceptor interceptor) {
        final HandlerInterceptor annotation = AnnotationUtils.getAnnotation(interceptor.getClass(), HandlerInterceptor.class);
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
