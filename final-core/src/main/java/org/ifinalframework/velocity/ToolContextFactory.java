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

package org.ifinalframework.velocity;

import lombok.RequiredArgsConstructor;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * ToolContextFactory.
 *
 * @author likly
 * @version 1.2.4
 * @since 1.2.4
 */
@RequiredArgsConstructor
public class ToolContextFactory implements ContextFactory {

    private final ToolManager toolManager;

    @Override
    @NonNull
    @SuppressWarnings("unchecked")
    public Context create(@Nullable Object param) {

        final ToolContext context = toolManager.createContext();
        if (param instanceof Map) {
            context.putAll((Map<String, Object>) param);
        }

        if (Objects.nonNull(param)) {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(param.getClass());
                for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                    Method readMethod = propertyDescriptor.getReadMethod();

                    if (Objects.isNull(readMethod)) {
                        continue;
                    }

                    context.put(propertyDescriptor.getName(), readMethod.invoke(param));
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        return context;
    }
}
