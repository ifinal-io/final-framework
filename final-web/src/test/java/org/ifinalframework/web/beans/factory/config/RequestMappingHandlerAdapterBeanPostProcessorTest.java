/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.web.beans.factory.config;

import org.ifinalframework.web.resolver.RequestJsonParamHandlerMethodArgumentResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * RequestMappingHandlerAdapterBeanPostProcessorTest.
 *
 * @author likly
 * @version 1.2.3
 * @since 1.2.3
 */
@ExtendWith(MockitoExtension.class)
class RequestMappingHandlerAdapterBeanPostProcessorTest {
    @InjectMocks
    private RequestMappingHandlerAdapterBeanPostProcessor requestMappingHandlerAdapterBeanPostProcessor;


    @Test
    void postProcessAfterInitialization() {
        final RequestMappingHandlerAdapter bean = (RequestMappingHandlerAdapter) requestMappingHandlerAdapterBeanPostProcessor
                .postProcessAfterInitialization(new RequestMappingHandlerAdapter(), RequestMappingHandlerAdapter.class.getSimpleName());


        Assertions.assertTrue(bean.getArgumentResolvers().get(0) instanceof RequestJsonParamHandlerMethodArgumentResolver);
    }
}
