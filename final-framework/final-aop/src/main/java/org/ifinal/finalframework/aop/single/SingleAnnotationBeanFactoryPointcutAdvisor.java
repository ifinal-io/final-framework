package org.ifinal.finalframework.aop.single;

import org.ifinal.finalframework.aop.AbsGenericPointcutAdvisor;
import org.ifinal.finalframework.aop.AnnotationBuilder;
import org.ifinal.finalframework.aop.AnnotationSource;
import org.ifinal.finalframework.aop.AnnotationSourceMethodPoint;
import org.ifinal.finalframework.aop.DefaultAnnotationMethodInterceptor;
import org.ifinal.finalframework.aop.InterceptorHandler;
import org.springframework.aop.Pointcut;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SingleAnnotationBeanFactoryPointcutAdvisor<A extends Annotation, E, T> extends AbsGenericPointcutAdvisor {

    private final Pointcut pointcut;

    protected SingleAnnotationBeanFactoryPointcutAdvisor(Class<A> annotationType, AnnotationBuilder<A, E> builder, List<InterceptorHandler<T, E>> handlers) {
        this(new SingleAnnotationSource<>(annotationType, builder), handlers);
    }

    protected SingleAnnotationBeanFactoryPointcutAdvisor(AnnotationSource<Collection<E>> source, List<InterceptorHandler<T, E>> handlers) {
        this.pointcut = new AnnotationSourceMethodPoint(source);
        setAdvice(new DefaultAnnotationMethodInterceptor<>(source, new SingleMethodInvocationDispatcher<T, E>(handlers) {
            @Override
            @NonNull
            protected T getExecutor(E annotation) {
                return SingleAnnotationBeanFactoryPointcutAdvisor.this.getExecutor(annotation);
            }
        }));
    }

    @Override
    @NonNull
    public Pointcut getPointcut() {
        return pointcut;
    }


    @NonNull
    protected abstract T getExecutor(E annotation);


}
