/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.ifinal.finalframework.aop;

import org.springframework.aop.PointcutAdvisor;
import org.springframework.core.Ordered;

import org.aopalliance.aop.Advice;

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
        final Advice advice = getAdvice();
        if (advice instanceof Ordered) {
            return ((Ordered) advice).getOrder();
        }
        return Ordered.LOWEST_PRECEDENCE;
    }

    public void setOrder(final int order) {

        this.order = order;
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }

}
