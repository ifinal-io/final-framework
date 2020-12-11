package org.ifinal.finalframework.retrofit.annotation;

import org.ifinal.finalframework.retrofit.RetrofitFactoryBean;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RetrofitScan {
    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Class<? extends Annotation> annotationClass() default Annotation.class;

    Class<?> markerInterface() default Class.class;

    Class<? extends RetrofitFactoryBean> factoryBean() default RetrofitFactoryBean.class;
}
