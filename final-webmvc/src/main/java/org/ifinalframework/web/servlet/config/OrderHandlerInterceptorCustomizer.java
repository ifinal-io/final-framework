/*
 * Copyright 2020-2022 the original author or authors.
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
 */

package org.ifinalframework.web.servlet.config;

import org.springframework.aop.support.AopUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

/**
 * Customize the order for {@link HandlerInterceptor} from {@link Ordered#getOrder()} or {@link Order#value()}.
 *
 * @author ilikly
 * @version 1.2.2
 * @see Ordered
 * @see Order
 * @since 1.2.2
 */
@Component
public class OrderHandlerInterceptorCustomizer implements HandlerInterceptorCustomizer {
    @Override
    public void customize(@NonNull InterceptorRegistration registration, @NonNull HandlerInterceptor interceptor) {
        final Class<?> targetClass = AopUtils.getTargetClass(interceptor);
        if (interceptor instanceof Ordered) {
            registration.order(((Ordered) interceptor).getOrder());
        } else {
            // find @Order
            final Order order = AnnotationUtils.getAnnotation(targetClass, Order.class);
            if (order != null) {
                registration.order(order.value());
            }
        }
    }
}
