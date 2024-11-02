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

package org.ifinalframework.context.result;

import org.springframework.stereotype.Component;

import org.ifinalframework.core.result.Result;

import java.util.List;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;

/**
 * Manage {@link ResultFunction} and {@link ResultConsumer}.
 *
 * @author iimik
 * @version 1.2.4
 * @see ResultFunction
 * @see ResultConsumer
 * @since 1.2.4
 */
@Component
@RequiredArgsConstructor
@SuppressWarnings({"rawtypes", "unchecked"})
public class ResultFunctionConsumerComposite implements Function<Object, Result<?>> {

    private final List<ResultFunction> functions;
    private final List<ResultConsumer> consumers;

    @Override
    public Result<?> apply(Object body) {

        final Result<?> result = functions.stream()
                .filter(it -> it.test(body))
                .findFirst()
                .map(it -> it.apply(body))
                .orElseThrow(() -> new UnsupportedOperationException("Not found ResultFunction for " + body));


        consumers.stream()
                .filter(it -> it.test(result))
                .forEach(it -> it.accept(result));


        return result;
    }
}
