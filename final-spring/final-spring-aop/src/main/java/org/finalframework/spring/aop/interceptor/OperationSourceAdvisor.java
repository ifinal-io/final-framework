package org.finalframework.spring.aop.interceptor;


import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationConfiguration;
import org.finalframework.spring.aop.OperationSource;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 00:20:16
 * @since 1.0
 */
public class OperationSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private final OperationSource<? extends Operation> source;
    private final OperationSourcePoint pointcut = new OperationSourcePoint() {
        @Override
        protected OperationSource<? extends Operation> getOperationSource() {
            return source;
        }
    };

    public OperationSourceAdvisor(OperationConfiguration configuration) {
        this(new AnnotationOperationSource<>(configuration));
    }

    public OperationSourceAdvisor(OperationSource<? extends Operation> source) {
        this.source = source;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
