package org.finalframework.data.trigger.interceptor;


import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 15:38:22
 * @since 1.0
 */
@Component
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

