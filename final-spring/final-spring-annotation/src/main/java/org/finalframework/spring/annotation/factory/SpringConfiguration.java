package org.finalframework.spring.annotation.factory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * Mark the type is a spring configuration element.
 * <p>
 * {@link SpringConfiguration}将标记了该注解的元素写到 {@code META-INF/spring.factories}文件中 {@code key} 为 {@link EnableAutoConfiguration}
 * 下，为{@code spring} 的内置组件功能。
 *
 * @author likly
 * @version 1.0
 * @date 2018-12-25 22:17:39
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.SOURCE)
@SpringFactory(EnableAutoConfiguration.class)
public @interface SpringConfiguration {
}
