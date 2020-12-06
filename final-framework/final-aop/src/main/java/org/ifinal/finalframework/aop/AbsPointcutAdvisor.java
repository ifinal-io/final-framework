package org.ifinal.finalframework.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.core.Ordered;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbsPointcutAdvisor implements PointcutAdvisor, Ordered {
    private Integer order;

    @Override
    public int getOrder() {
        if (this.order != null) {
            return this.order;
        }
        Advice advice = getAdvice();
        if (advice instanceof Ordered) {
            return ((Ordered) advice).getOrder();
        }
        return Ordered.LOWEST_PRECEDENCE;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }
}
