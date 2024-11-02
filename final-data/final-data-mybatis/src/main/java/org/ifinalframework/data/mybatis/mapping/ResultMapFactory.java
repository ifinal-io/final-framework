/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.data.mybatis.mapping;

import org.springframework.lang.NonNull;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;

/**
 * Create a {@link ResultMap} from {@linkplain Class entity} with {@link Configuration}.
 *
 * @author iimik
 * @version 1.2.2
 * @since 1.2.2
 */
@FunctionalInterface
public interface ResultMapFactory {
    /**
     * return the {@link ResultMap} of {@link Class}.
     *
     * @param configuration configuration.
     * @param entity        entity
     * @return result map
     */
    @NonNull
    ResultMap create(@NonNull Configuration configuration, @NonNull final Class<?> entity);
}
