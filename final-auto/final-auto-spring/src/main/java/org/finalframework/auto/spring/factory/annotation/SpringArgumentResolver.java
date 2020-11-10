package org.finalframework.auto.spring.factory.annotation;

import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.lang.annotation.*;

/**
 * 自定义参数解析器
 *
 * @author likly
 * @version 1.0
 * @date 2019-04-15 09:28:08
 * @see HandlerMethodArgumentResolver
 * @since 1.0
 */
@Documented
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringFactory(value = HandlerMethodArgumentResolver.class, extend = true)
public @interface SpringArgumentResolver {
}
