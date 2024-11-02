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

package org.ifinalframework.data.query;

import org.ifinalframework.data.query.condition.BetweenCondition;
import org.ifinalframework.data.query.condition.CompareCondition;
import org.ifinalframework.data.query.condition.InCondition;
import org.ifinalframework.data.query.condition.JsonCondition;
import org.ifinalframework.data.query.condition.LikeCondition;
import org.ifinalframework.data.query.condition.NullCondition;

/**
 * Criteriable.
 *
 * @author iimik
 * @version 1.0.0
 * @see FunctionCriteriable
 * @since 1.0.0
 */
public interface Criteriable<V> extends NullCondition, CompareCondition<V>, BetweenCondition<V>, InCondition,
    LikeCondition
    , JsonCondition<V> {

}
