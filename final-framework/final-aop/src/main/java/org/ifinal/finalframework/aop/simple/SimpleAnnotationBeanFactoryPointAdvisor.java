package org.ifinal.finalframework.aop.simple;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import org.ifinal.finalframework.aop.AbsGenericPointcutAdvisor;
import org.ifinal.finalframework.aop.AnnotationSourceMethodPoint;
import org.ifinal.finalframework.aop.DefaultAnnotationMethodInterceptor;
import org.ifinal.finalframework.aop.InterceptorHandler;
import org.springframework.aop.Pointcut;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SimpleAnnotationBeanFactoryPointAdvisor<T> extends AbsGenericPointcutAdvisor {

    private final Pointcut pointcut;

    protected SimpleAnnotationBeanFactoryPointAdvisor(final Collection<Class<? extends Annotation>> annotationTypes,
        final List<InterceptorHandler<T, Boolean>> handlers) {

        SimpleAnnotationSource source = new SimpleAnnotationSource(annotationTypes);
        this.pointcut = new AnnotationSourceMethodPoint(source);
        this.setAdvice(new DefaultAnnotationMethodInterceptor<>(source, new SimpleMethodInvocationDispatcher<T>(handlers) {

            @Override
            protected T getExecutor() {

                return null;
            }
        }));
    }

    @Override
    @NonNull
    public Pointcut getPointcut() {
        return pointcut;
    }

    protected abstract T getExecutor();

}
