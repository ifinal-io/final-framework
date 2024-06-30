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

import org.springframework.core.MethodParameter;

import java.util.function.Predicate;

/**
 * Rest {@link MethodParameter} 方法过滤器。
 *
 * @author iimik
 * @version 1.0.0
 * @see org.springframework.web.bind.annotation.ResponseBody
 * @since 1.0.0
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice#supports(MethodParameter, Class)
 */
@FunctionalInterface
public interface ResponseBodyMethodParameterPredicate extends Predicate<MethodParameter> {

}
