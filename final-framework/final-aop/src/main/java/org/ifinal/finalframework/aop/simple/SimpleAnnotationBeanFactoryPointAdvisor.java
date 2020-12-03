package org.ifinal.finalframework.aop.simple;

import org.ifinal.finalframework.aop.AnnotationSourceMethodPoint;
import org.ifinal.finalframework.aop.DefaultAnnotationMethodInterceptor;
import org.ifinal.finalframework.aop.InterceptorHandler;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SimpleAnnotationBeanFactoryPointAdvisor<T> extends AbstractBeanFactoryPointcutAdvisor {

    private final Collection<Class<? extends Annotation>> annotationTypes;
    private final List<InterceptorHandler<T, Boolean>> handlers;

    private final Pointcut pointcut;

    public SimpleAnnotationBeanFactoryPointAdvisor(Collection<Class<? extends Annotation>> annotationTypes, List<InterceptorHandler<T, Boolean>> handlers) {
        this.annotationTypes = annotationTypes;
        this.handlers = handlers;

        SimpleAnnotationSource source = new SimpleAnnotationSource(this.annotationTypes);
        this.pointcut = new AnnotationSourceMethodPoint(source);
        this.setAdvice(new DefaultAnnotationMethodInterceptor<>(source, new SimpleMethodInvocationDispatcher<T>(this.handlers) {
            @Override
            protected T getExecutor() {
                return null;
            }
        }));
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    protected abstract T getExecutor();
}
