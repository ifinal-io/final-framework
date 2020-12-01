package org.ifinal.finalframework.aop.interceptor;


import org.ifinal.finalframework.aop.OperationConfiguration;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.data.util.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@SuppressWarnings("serial")
public class AnnotationBeanFactoryPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    private final Lazy<Pointcut> pointcut;

    protected AnnotationBeanFactoryPointcutAdvisor(OperationConfiguration configuration) {
        this.setAdvice(configuration.getInterceptor());
        this.pointcut = Lazy.of(configuration.getPointcut());
    }

    @Override
    @NonNull
    public Pointcut getPointcut() {
        return pointcut.get();
    }
}
