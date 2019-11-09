package org.finalframework.spring.web.resolver.annotation;

import org.finalframework.coding.spring.factory.annotation.SpringFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 自定义参数解析器
 *
 * @author likly
 * @version 1.0
 * @date 2019-04-15 09:28:08
 * @since 1.0
 */
@Documented
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringFactory(value = ArgumentResolver.class,expand = true)
public @interface ArgumentResolver {
}
