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

import lombok.RequiredArgsConstructor;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.ConfigurationUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * Velocity {@link ToolContext} factory.
 *
 * @author iimik
 * @version 1.2.4
 * @see ToolManager
 * @since 1.2.4
 */
@RequiredArgsConstructor
public class ToolContextFactory implements ContextFactory {

    private final ToolManager toolManager;

    public ToolContextFactory() {
        this(new ToolManager());
        toolManager.configure(ConfigurationUtils.getDefaultTools());
    }

    @Override
    @NonNull
    public Context create(@Nullable Object param) {


        if (Objects.isNull(param)) {
            return toolManager.createContext();
        }

        // Process map
        if (param instanceof Map) {
            return fromMap((Map) param);
        }

        // Process Bean
        return fromBean(param);
    }

    private Context fromMap(Map param) {
        final ToolContext context = toolManager.createContext();
        context.putAll(param);
        return context;
    }

    private Context fromBean(Object bean) {
        final ToolContext context = toolManager.createContext();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                Method readMethod = propertyDescriptor.getReadMethod();

                if (Objects.isNull(readMethod)) {
                    continue;
                }

                context.put(propertyDescriptor.getName(), readMethod.invoke(bean));
            }
        } catch (Exception e) {
            throw new VelocityException(e);
        }
        return context;
    }
}
