package org.finalframework.coding.spring.factory.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * {@link SpringFactory}注解实现将标注了（直接或间接）该注解的 {@link Class}元素按一定的规则写入到 {@code META-INF/spring.factories}
 * 文件中，提高开发效率，写入的 {@code META-INF/spring.factories}文件中。
 *
 * <p>使用方式：</p>
 * <ul>
 *     <li>直接标记在目标{@link Class}上</li>
 *     <li>标记在{@link Annotation}元素上，再即该{@link Annotation}标记在目标 {@link Class}上。</li>
 *     <li>标记在{@link Package}元素上，再即该{@link Annotation}标记在目标 {@link Class}上。</li>
 * </ul>
 *
 * @author likly
 * @version 1.0
 * @date 2019-11-06 09:24:44
 * @see org.springframework.context.ApplicationListener
 * @see org.springframework.boot.autoconfigure.EnableAutoConfiguration
 * @since 1.0
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(SpringFactory.List.class)
public @interface SpringFactory {
    /**
     * 工具接口类，也可以是 {@link Annotation}
     */
    Class<?> value();

    /**
     * 是否为扩展，默认 {@code false}。
     */
    boolean expand() default false;

    @Target({PACKAGE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        SpringFactory[] value();
    }
}
