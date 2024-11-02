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

package org.ifinalframework.data.mybatis.reflection.factory;


import org.springframework.core.ResolvableType;

import org.apache.ibatis.reflection.factory.ObjectFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.ServiceLoader;

import lombok.extern.slf4j.Slf4j;

/**
 * ObjectFactoryWrapper.
 *
 * @author iimik
 * @version 1.4.2
 * @since 1.4.2
 */
@Slf4j
@SuppressWarnings("rawtypes")
public class ObjectFactoryWrapper implements ObjectFactory {
    private final ObjectFactory target;
    private final Map<Class, ObjectFactoryInterceptor> objectFactoryInterceptorMap = new LinkedHashMap<>();

    public ObjectFactoryWrapper(ObjectFactory target) {
        this.target = target;
        for (ObjectFactoryInterceptor interceptor : ServiceLoader.load(ObjectFactoryInterceptor.class)) {
            Class<?> type = ResolvableType.forClass(interceptor.getClass()).as(ObjectFactoryInterceptor.class).resolveGeneric(0);
            objectFactoryInterceptorMap.put(type, interceptor);
        }
    }

    @Override
    public <T> T create(Class<T> type) {
        return create(type, null, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        ObjectFactoryInterceptor interceptor = objectFactoryInterceptorMap.get(type);
        if (Objects.nonNull(interceptor)) {
            return (T) interceptor.create(type, constructorArgTypes, constructorArgs);
        }

        return target.create(type, constructorArgTypes, constructorArgs);
    }

    @Override
    public <T> boolean isCollection(Class<T> type) {
        return target.isCollection(type);
    }

    @Override
    public void setProperties(Properties properties) {
        target.setProperties(properties);
    }
}


