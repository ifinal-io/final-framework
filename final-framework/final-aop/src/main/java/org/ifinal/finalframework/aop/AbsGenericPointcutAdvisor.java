package org.ifinal.finalframework.aop;

import org.aopalliance.aop.Advice;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbsGenericPointcutAdvisor extends AbsPointcutAdvisor {

    private Advice advice = EMPTY_ADVICE;

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(final Advice advice) {

        this.advice = advice;
    }

}
