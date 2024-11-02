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

package org.ifinalframework.data.query.update;

import java.util.function.Consumer;

import org.springframework.lang.Nullable;

import org.ifinalframework.data.query.CriterionAttributes;
import org.ifinalframework.data.query.Update;

/**
 * Updatable.
 *
 * @author iimik
 * @version 1.3.5
 * @since 1.3.5
 */
@FunctionalInterface
public interface Updatable {
    default Update update(String expression, String column, Object value) {
        return update(expression, column, value, null);
    }

    Update update(String expression, String column, Object value,@Nullable Consumer<CriterionAttributes> consumer);
}
