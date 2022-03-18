/*
 * Copyright 2020-2021 the original author or authors.
 *
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

import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.TypedValue;
import org.springframework.lang.Nullable;

import java.util.Map;

/**
 * NoCheckContainsKeyMapAccessor.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public class PropertyMapAccessor extends MapAccessor {

    @Override
    public boolean canRead(final EvaluationContext context, @Nullable final Object target, final String name)
        throws AccessException {
        return target instanceof Map;
    }

    @Override
    public TypedValue read(final EvaluationContext context, @Nullable final Object target, final String name)
        throws AccessException {

        if (target == null) {
            return TypedValue.NULL;
        }

        Map<?, ?> map = (Map<?, ?>) target;
        Object value = map.get(name);
        if (value == null) {
            return TypedValue.NULL;
        }
        return new TypedValue(value);
    }

}
