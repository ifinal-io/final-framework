package org.finalframework.retrofit.annotation;

import org.finalframework.retrofit.RetrofitFactoryBean;

import java.lang.annotation.*;


/**
 * @author likly
 * @version 1.0
 * @date 2020-04-25 16:49:43
 * @since 1.0
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
