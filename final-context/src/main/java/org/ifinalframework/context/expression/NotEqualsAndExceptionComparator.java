/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.context.expression;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * @author likly
 * @version 1.3.0
 **/
public class NotEqualsAndExceptionComparator implements Comparator {
    @Override
    public boolean compare(@NonNull String expression, @Nullable Object leftValue, @Nullable Object rightValue, @Nullable Exception e) {

        if (Objects.nonNull(e)) {
            return false;
        }

        return ObjectUtils.nullSafeEquals(leftValue, rightValue);
    }
}
