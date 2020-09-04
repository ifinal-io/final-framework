/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.spring.web.response.advice;

import org.finalframework.core.filter.Filter;
import org.finalframework.spring.web.response.annotation.ResponseIgnore;
import org.finalframework.spring.web.response.annotation.RestResponseController;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest {@link MethodParameter} 方法过滤器。
 *
 * @author likly
 * @version 1.0
 * @date 2019-09-27 10:53:56
 * @since 1.0
 */
public class RestMethodParameterFilter implements Filter<MethodParameter> {

    @Override
    public boolean matches(MethodParameter methodParameter) {
        if (methodParameter.hasMethodAnnotation(ResponseIgnore.class) || methodParameter.getDeclaringClass().isAnnotationPresent(ResponseIgnore.class)) {
            return false;
        }

        if (methodParameter.getDeclaringClass().isAnnotationPresent(RestResponseController.class)) {
            return true;
        }

        return methodParameter.hasMethodAnnotation(ResponseBody.class) || methodParameter.getDeclaringClass().isAnnotationPresent(RestController.class);
    }
}
