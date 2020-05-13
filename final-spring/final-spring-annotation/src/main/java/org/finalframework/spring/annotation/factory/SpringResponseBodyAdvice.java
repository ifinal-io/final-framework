package org.finalframework.spring.annotation.factory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-06 12:56:50
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@SpringFactory(value = ResponseBodyAdvice.class,expand = true)
public @interface SpringResponseBodyAdvice {
}
