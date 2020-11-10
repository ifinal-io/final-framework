package org.finalframework.aop.interceptor;


import org.finalframework.aop.OperationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.data.util.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 00:20:16
 * @since 1.0
 */
@Component
public class AnnotationPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationPointcutAdvisor.class);
    private final Lazy<Pointcut> pointcut;

    protected AnnotationPointcutAdvisor(OperationConfiguration configuration) {
        this.setAdvice(configuration.getInterceptor());
        this.pointcut = Lazy.of(configuration.getPointcut());
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut.get();
    }
}
