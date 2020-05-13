package org.finalframework.spring.annotation.factory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;

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
@SpringFactory(value = SpringArgumentResolver.class, expand = true)
public @interface SpringArgumentResolver {
}
