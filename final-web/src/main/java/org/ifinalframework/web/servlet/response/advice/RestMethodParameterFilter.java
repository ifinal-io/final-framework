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

package org.ifinalframework.web.servlet.response.advice;

import org.ifinalframework.util.function.Filter;
import org.ifinalframework.web.servlet.response.annotation.ResponseIgnore;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest {@link MethodParameter} 方法过滤器。
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class RestMethodParameterFilter implements Filter<MethodParameter> {

    public static final RestMethodParameterFilter INSTANCE = new RestMethodParameterFilter();

    @Override
    public boolean matches(final MethodParameter methodParameter) {

        if (methodParameter.hasMethodAnnotation(ResponseIgnore.class) || methodParameter.getDeclaringClass()
                .isAnnotationPresent(ResponseIgnore.class)) {
            return false;
        }

        return methodParameter.hasMethodAnnotation(ResponseBody.class) || methodParameter.getDeclaringClass()
                .isAnnotationPresent(RestController.class);
    }

}
