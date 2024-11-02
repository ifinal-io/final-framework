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

package org.ifinalframework.data.query;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DefaultQEntityFactory.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultQEntityFactory implements QEntityFactory {

    public static final QEntityFactory INSTANCE = new DefaultQEntityFactory();

    private final Map<Class<?>, QEntity<Serializable, ?>> cache = new ConcurrentHashMap<>();

    @Override
    public QEntity<?, ?> create(final Class<?> entity) {
        return cache.computeIfAbsent(entity, clazz -> new AbsQEntity<>(entity));
    }

}
