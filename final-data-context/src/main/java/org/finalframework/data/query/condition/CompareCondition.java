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

package org.finalframework.data.query.condition;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 00:21:27
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface CompareCondition<V, R> extends Condition {
    R eq(@NonNull V value);

    R neq(@NonNull V value);

    R gt(@NonNull V value);

    R gte(@NonNull V value);

    R lt(@NonNull V value);

    R lte(@NonNull V value);

    default R before(@NonNull V value) {
        return lt(value);
    }

    default R after(@NonNull V value) {
        return gt(value);
    }
}
