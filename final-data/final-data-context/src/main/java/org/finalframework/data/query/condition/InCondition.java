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

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 00:21:43
 * @since 1.0
 */
@SuppressWarnings("all")
public interface InCondition<V, R> extends Condition {

    default R in(@NonNull V... values) {
        return in(Arrays.asList(values));
    }

    R in(@NonNull Collection<V> values);


    default R nin(@NonNull V... values) {
        return in(Arrays.asList(values));
    }

    R nin(@NonNull Collection<V> values);

}
