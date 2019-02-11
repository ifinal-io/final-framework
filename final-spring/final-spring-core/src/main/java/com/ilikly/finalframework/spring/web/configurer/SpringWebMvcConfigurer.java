package com.ilikly.finalframework.spring.web.configurer;

import com.ilikly.finalframework.spring.web.resolver.RequestJsonParamHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-11 10:47:25
 * @since 1.0
 */
public class SpringWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(0,new RequestJsonParamHandlerMethodArgumentResolver());
    }
}
