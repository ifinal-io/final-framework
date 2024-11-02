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

package org.ifinalframework.velocity;

import org.apache.velocity.context.Context;
import org.apache.velocity.exception.VelocityException;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Velocity {@link Context} factory.
 *
 * @author iimik
 * @version 1.2.4
 * @see Context
 * @since 1.2.4
 */
@FunctionalInterface
public interface ContextFactory {
    /**
     * Create a {@link Context} from {@code param}.
     *
     * @param param context param.
     * @throws VelocityException velocity context exception.
     */
    @NonNull
    Context create(@Nullable Object param) throws VelocityException;
}
