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

package org.ifinalframework.data.domain.spi;

import org.springframework.stereotype.Component;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IException;
import org.ifinalframework.core.IUser;
import org.ifinalframework.data.spi.SpiAction;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * DefaultLoggerAfterConsumer.
 *
 * @author iimik
 * @version 1.5.1
 * @since 1.5.1
 */
@Slf4j
@Component
public class DefaultLoggerAfterConsumer implements LoggerAfterConsumer {
    @Override
    public void accept(SpiAction action, IEntity<?> entity, Object param, Object value, Object result, IUser<?> user, Throwable e) {
        if (Objects.isNull(e)) {
            logger.info("action={},entity={},param={},value={},result={}", action, entity, param, value, result);
        } else if (e instanceof IException) {
            logger.warn("action={},entity={},param={},value={},result={},code={},message={}",
                    action, entity, param, value, result, ((IException) e).getCode(), e.getMessage());
        } else {
            logger.error("action={},entity={},param={},value={},result={}", action, entity, param, value, result, e);
        }
    }
}
