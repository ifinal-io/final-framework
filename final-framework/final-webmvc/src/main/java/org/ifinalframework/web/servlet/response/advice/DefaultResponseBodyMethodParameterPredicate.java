/*
 * Copyright 2020-2024 the original author or authors.
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


import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.ifinalframework.web.servlet.response.annotation.ResponseIgnore;

/**
 * 被{@link ResponseBody}（直接标记或间接）标记的{@link MethodParameter}就是{@code REST}方法。
 *
 * @author iimik
 * @see ResponseIgnore
 * @since 1.6.0
 **/
public class DefaultResponseBodyMethodParameterPredicate implements ResponseBodyMethodParameterPredicate {

    public static final DefaultResponseBodyMethodParameterPredicate INSTANCE = new DefaultResponseBodyMethodParameterPredicate();

    @Override
    public boolean test(final MethodParameter methodParameter) {

        Class<?> clazz = methodParameter.getDeclaringClass();

        if (methodParameter.hasMethodAnnotation(ResponseIgnore.class) || clazz
                .isAnnotationPresent(ResponseIgnore.class)) {
            return false;
        }

        return methodParameter.hasMethodAnnotation(ResponseBody.class) || clazz
                .isAnnotationPresent(RestController.class);
    }
}
