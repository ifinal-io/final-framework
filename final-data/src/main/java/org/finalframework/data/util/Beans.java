package org.finalframework.data.util;

import org.finalframework.util.Asserts;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public interface Beans {

    /**
     * return the beans annotated by specified annotation.
     *
     * @param applicationContext spring application context
     * @param annotationType     the specified annotation
     * @param <T>                the target beans type
     */
    @SuppressWarnings("unchecked")
    static <T> List<T> findBeansByAnnotation(ApplicationContext applicationContext, Class<? extends Annotation> annotationType) {
        return findAllBeansAnnotatedBy(applicationContext, annotationType)
                .map(it -> (T) it)
                .collect(Collectors.toList());
    }

    static Stream<String> findAllBeans(ApplicationContext applicationContext) {
        Asserts.isNull(applicationContext, "applicationContext must be not null!");
        return Arrays.stream(BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, Object.class));
    }

    static Stream<Object> findAllBeansAnnotatedBy(ApplicationContext applicationContext, Class<? extends Annotation> annotationType) {
        Asserts.isNull(applicationContext, "applicationContext must be not null!");
        Asserts.isNull(annotationType, "annotationType must be not null!");
        return findAllBeans(applicationContext).filter(name -> applicationContext.findAnnotationOnBean(name, annotationType) != null)
                .map(applicationContext::getBean);
    }


}
