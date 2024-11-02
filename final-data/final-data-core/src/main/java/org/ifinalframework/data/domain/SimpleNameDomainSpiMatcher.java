/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.domain;

import org.springframework.aop.support.AopUtils;
import org.springframework.lang.NonNull;

import org.ifinalframework.data.spi.SpiAction;

import lombok.extern.slf4j.Slf4j;

/**
 * SimpleNameDomainSpiMatcher.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@Slf4j
public class SimpleNameDomainSpiMatcher implements DomainSpiMatcher {
    @Override
    public boolean matches(@NonNull Object spi, @NonNull SpiAction action, @NonNull SpiAction.Advice advice) {
        final Class<?> targetClass = AopUtils.getTargetClass(spi);
        for (final String name : action.getValues()) {
            final String adviceName = advice.getValue() + name;
            if (targetClass.getSimpleName().contains(adviceName)) {
                logger.info("found type={} for action={} with advice={}", targetClass, action, advice);
                return true;
            }
        }
        return false;
    }
}
