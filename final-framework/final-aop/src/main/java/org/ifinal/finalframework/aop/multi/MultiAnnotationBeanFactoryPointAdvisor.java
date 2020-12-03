package org.ifinal.finalframework.aop.multi;

import org.ifinal.finalframework.aop.AnnotationBuilder;
import org.ifinal.finalframework.aop.AnnotationSourceMethodPoint;
import org.ifinal.finalframework.aop.DefaultAnnotationMethodInterceptor;
import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.aop.single.SingleAnnotationSource;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.lang.NonNull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class MultiAnnotationBeanFactoryPointAdvisor<A, E> extends AbstractBeanFactoryPointcutAdvisor {

    private final Pointcut pointcut;

    private final MultiAnnotationSource<A> source = new MultiAnnotationSource<>();
    private final MultiValueMap<Class<? extends Annotation>, InterceptorHandler<E, A>> handlers = new LinkedMultiValueMap<>();

    public MultiAnnotationBeanFactoryPointAdvisor() {
        this.pointcut = new AnnotationSourceMethodPoint(source);
        this.setAdvice(new DefaultAnnotationMethodInterceptor<>(source, new MultiMethodInvocationDispatcher<E, A>(handlers) {
            @Override
            protected E getExecutor(A annotation) {
                return MultiAnnotationBeanFactoryPointAdvisor.this.getExecutor(annotation);
            }
        }));
    }

    public <T extends Annotation> void addAnnotation(Class<T> annotationType, AnnotationBuilder<T, A> builder, InterceptorHandler<E, A> handler) {
        this.source.addAnnotationSource(annotationType, new SingleAnnotationSource<>(annotationType, builder));
        this.handlers.add(annotationType, handler);
    }


    @Override
    @NonNull
    public Pointcut getPointcut() {
        return pointcut;
    }


    @NonNull
    protected abstract E getExecutor(A annotation);

}
