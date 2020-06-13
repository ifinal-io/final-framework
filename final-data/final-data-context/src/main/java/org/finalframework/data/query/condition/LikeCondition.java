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
 * @date 2019-11-24 00:24:29
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface LikeCondition<R> extends Condition {
    default R startWith(@NonNull String value) {
        return like("%" + value);
    }

    default R notStartWith(@NonNull String value) {
        return notLike("%" + value);
    }

    default R endWith(@NonNull String value) {
        return like(value + "%");
    }

    default R notEndWith(@NonNull String value) {
        return notLike(value + "%");
    }

    default R contains(@NonNull String value) {
        return like("%" + value + "%");
    }

    default R notContains(@NonNull String value) {
        return notLike("%" + value + "%");
    }

    R like(@NonNull String value);

    R notLike(@NonNull String value);

}
