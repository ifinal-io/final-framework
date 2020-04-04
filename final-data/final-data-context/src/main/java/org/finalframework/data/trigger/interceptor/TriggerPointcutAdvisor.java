package org.finalframework.data.trigger.interceptor;


import org.finalframework.spring.annotation.factory.SpringComponent;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

import javax.annotation.PostConstruct;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 15:38:22
 * @since 1.0
 */
@SpringComponent
public class TriggerPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {


    @PostConstruct
    public void init() {
        this.setAdviceBeanName("triggerMethodInterceptor");
    }

    @Override
    public Pointcut getPointcut() {
        return new TriggerPointcut();
    }


}

