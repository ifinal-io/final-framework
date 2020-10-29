package org.finalframework.web.autoconfiguration;


import org.finalframework.auto.spring.factory.annotation.SpringApplicationListener;
import org.finalframework.auto.spring.factory.annotation.SpringArgumentResolver;
import org.finalframework.context.util.Beans;
import org.finalframework.util.Asserts;
import org.finalframework.web.http.converter.JsonStringHttpMessageConverter;
import org.finalframework.web.resolver.RequestJsonParamHandlerMethodArgumentResolver;
import org.finalframework.web.resolver.annotation.RequestJsonParam;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.method.annotation.MapMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMessageConverterMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义参数解析器配置器。
 * <p>
 * 由于 {@link RequestMappingHandlerAdapter}的配置的 {@link HandlerMethodArgumentResolver}在默认的配置之后，
 * 因此当参数列表中使用{@link java.util.Map}或其子类作为参数时，会被默认的 {@link MapMethodProcessor}所解析，走不到自定义的参数解析器，
 * 因此在 {@link ApplicationReadyEvent}事件中，将自定义的 {@link HandlerMethodArgumentResolver}置于默认的之前。
 *
 * @author likly
 * @version 1.0
 * @date 2019-04-15 09:30:10
 * @since 1.0
 */
@Configuration
@SpringApplicationListener
@SuppressWarnings("all")
public class RequestMappingHandlerAdapterAutoConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * {@link RequestJsonParam} 参数注解解析器
     *
     * @return
     * @see RequestJsonParam
     * @see RequestJsonParamHandlerMethodArgumentResolver
     */
    @Bean
    public RequestJsonParamHandlerMethodArgumentResolver requestJsonParamHandlerMethodArgumentResolver() {
        return new RequestJsonParamHandlerMethodArgumentResolver();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        final RequestMappingHandlerAdapter requestMappingHandlerAdapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);
        configureMessageConverters(applicationContext, requestMappingHandlerAdapter);
        configureHandlerMethodArgumentResolver(applicationContext, requestMappingHandlerAdapter);
        configureHandlerReturnValueHandler(applicationContext, requestMappingHandlerAdapter);
    }

    /**
     * @param context
     * @param adapter
     * @see RequestMappingHandlerAdapter#setArgumentResolvers(List)
     */
    private void configureHandlerMethodArgumentResolver(ApplicationContext context, RequestMappingHandlerAdapter adapter) {

        final List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
        final List<HandlerMethodArgumentResolver> customerArgumentResolvers = Beans.findBeansByAnnotation(context, SpringArgumentResolver.class);
        if (Asserts.nonEmpty(customerArgumentResolvers)) {
            //自定义参数解析器不为空，将自定义的参数解析器置于默认的之前
            argumentResolvers.addAll(customerArgumentResolvers);
        }

        // 获取默认的参数解析器
        final List<HandlerMethodArgumentResolver> defaultArgumentResolvers = adapter.getArgumentResolvers();
        if (Asserts.nonEmpty(defaultArgumentResolvers)) {


            for (HandlerMethodArgumentResolver argumentResolver : defaultArgumentResolvers) {
                if (argumentResolver instanceof AbstractMessageConverterMethodArgumentResolver) {
                    Field messageConverters = ReflectionUtils.findField(argumentResolver.getClass(), "messageConverters");
                    messageConverters.setAccessible(true);
                    ReflectionUtils.setField(messageConverters, argumentResolver, adapter.getMessageConverters());
                }
                argumentResolvers.add(argumentResolver);
            }
        }


        adapter.setArgumentResolvers(argumentResolvers);
    }

    private void configureHandlerReturnValueHandler(ApplicationContext context, RequestMappingHandlerAdapter adapter) {

        final List<HandlerMethodReturnValueHandler> returnValueHandlers = new LinkedList<>();

        List<HandlerMethodReturnValueHandler> defualtReturnValueHandlers = adapter.getReturnValueHandlers();
        if (Asserts.nonEmpty(defualtReturnValueHandlers)) {


            for (HandlerMethodReturnValueHandler returnValueHandler : defualtReturnValueHandlers) {
                if (returnValueHandler instanceof AbstractMessageConverterMethodProcessor) {
                    Field messageConverters = ReflectionUtils.findField(returnValueHandler.getClass(), "messageConverters");
                    messageConverters.setAccessible(true);
                    ReflectionUtils.setField(messageConverters, returnValueHandler, adapter.getMessageConverters());
                }
                returnValueHandlers.add(returnValueHandler);
            }
        }


        adapter.setReturnValueHandlers(returnValueHandlers);
    }

    /**
     * @param context
     * @param adapter
     * @see RequestMappingHandlerAdapter#getMessageConverters()
     */
    private void configureMessageConverters(ApplicationContext context, RequestMappingHandlerAdapter adapter) {

        List<HttpMessageConverter<?>> messageConverters = adapter.getMessageConverters();


        List<HttpMessageConverter<?>> httpMessageConverters = messageConverters.stream()
                .map(it -> {
                    if (it instanceof StringHttpMessageConverter) {
                        JsonStringHttpMessageConverter jsonStringHttpMessageConverter = new JsonStringHttpMessageConverter((StringHttpMessageConverter) it);
                        return jsonStringHttpMessageConverter;
                    } else {
                        return it;
                    }
                }).collect(Collectors.toList());


        adapter.setMessageConverters(httpMessageConverters);
    }

}