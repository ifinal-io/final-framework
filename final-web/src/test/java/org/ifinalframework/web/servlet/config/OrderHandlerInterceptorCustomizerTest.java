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

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * OrderHandlerInterceptorCustomizerTest.
 *
 * @author ilikly
 * @version 1.4.0
 * @since 1.4.0
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class OrderHandlerInterceptorCustomizerTest {

    /**
     * @see Ordered
     */
    @Test
    void orderedInterface() {
        InterceptorRegistration registration = mock(InterceptorRegistration.class);
        OrderHandlerInterceptorCustomizer customizer = new OrderHandlerInterceptorCustomizer();

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(int.class);
        customizer.customize(registration, new OrderedHandlerInterceptor());
        verify(registration, only()).order(captor.capture());
        assertEquals(Ordered.HIGHEST_PRECEDENCE, captor.getValue());
    }

    /**
     * @see Order
     */
    @Test
    void orderAnnotation() {
        InterceptorRegistration registration = mock(InterceptorRegistration.class);
        OrderHandlerInterceptorCustomizer customizer = new OrderHandlerInterceptorCustomizer();

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(int.class);
        customizer.customize(registration, new OrderAnnotationHandlerInterceptor());
        verify(registration, only()).order(captor.capture());
        assertEquals(Ordered.HIGHEST_PRECEDENCE, captor.getValue());
    }

    private static class OrderedHandlerInterceptor implements HandlerInterceptor, Ordered {
        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    private static class OrderAnnotationHandlerInterceptor implements HandlerInterceptor {

    }


}