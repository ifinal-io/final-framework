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
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-20 20:43:22
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface JsonCondition<V, R> extends ExecutableCondition {

    /**
     */
    default R jsonContains(@NonNull V value) {
        return jsonContains(value, null);
    }

    /**
     */
    R jsonContains(@NonNull V value, @Nullable String path);

    /**
     */
    default R notJsonContains(@NonNull V value) {
        return notJsonContains(value, null);
    }

    /**
     */
    R notJsonContains(@NonNull V value, @Nullable String path);

}
