package org.finalframework.spring.web.autoconfigure;


import org.finalframework.core.Assert;
import org.finalframework.data.util.BeanUtils;
import org.finalframework.spring.coding.ApplicationEventListener;
import org.finalframework.spring.coding.AutoConfiguration;
import org.finalframework.spring.web.http.converter.JsonStringHttpMessageConverter;
import org.finalframework.spring.web.resolver.RequestJsonParamHandlerMethodArgumentResolver;
import org.finalframework.spring.web.resolver.annotation.ArgumentResolver;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.annotation.MapMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

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
@AutoConfiguration
@ApplicationEventListener
@SuppressWarnings("all")
public class RequestMappingHandlerAdapterAutoConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * {@link org.finalframework.spring.web.resolver.annotation.RequestJsonParam} 参数注解解析器
     *
     * @return
     * @see org.finalframework.spring.web.resolver.annotation.RequestJsonParam
     * @see org.finalframework.spring.web.resolver.RequestJsonParamHandlerMethodArgumentResolver
     */
    @Bean
    public RequestJsonParamHandlerMethodArgumentResolver requestJsonParamHandlerMethodArgumentResolver() {
        return new RequestJsonParamHandlerMethodArgumentResolver();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        final RequestMappingHandlerAdapter requestMappingHandlerAdapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);

        configureHandlerMethodArgumentResolver(applicationContext, requestMappingHandlerAdapter);
        configureMessageConverters(applicationContext, requestMappingHandlerAdapter);

    }

    /**
     * @param context
     * @param adapter
     * @see RequestMappingHandlerAdapter#setArgumentResolvers(List)
     */
    private void configureHandlerMethodArgumentResolver(ApplicationContext context, RequestMappingHandlerAdapter adapter) {

        final List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
        final List<HandlerMethodArgumentResolver> customerArgumentResolvers = BeanUtils.findBeansByAnnotation(context, ArgumentResolver.class);
        if (Assert.nonEmpty(customerArgumentResolvers)) {
            //自定义参数解析器不为空，将自定义的参数解析器置于默认的之前
            argumentResolvers.addAll(customerArgumentResolvers);
        }

        // 获取默认的参数解析器
        final List<HandlerMethodArgumentResolver> defaultArgumentResolvers = adapter.getArgumentResolvers();
        if (Assert.nonEmpty(defaultArgumentResolvers)) {
            argumentResolvers.addAll(defaultArgumentResolvers);
        }

        adapter.setArgumentResolvers(argumentResolvers);
    }

    /**
     * @param context
     * @param adapter
     * @see RequestMappingHandlerAdapter#getMessageConverters()
     */
    private void configureMessageConverters(ApplicationContext context, RequestMappingHandlerAdapter adapter) {
        adapter.getMessageConverters().add(0, new JsonStringHttpMessageConverter());
    }

}
