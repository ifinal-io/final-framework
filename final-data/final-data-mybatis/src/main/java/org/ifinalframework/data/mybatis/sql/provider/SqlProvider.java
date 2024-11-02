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

package org.ifinalframework.data.mybatis.sql.provider;

import org.springframework.lang.NonNull;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;

import java.util.Map;

/**
 * A sql provider for {@link ProviderSqlSource}.
 *
 * @author iimik
 * @version 1.0.0
 * @see ProviderSqlSource
 * @see org.apache.ibatis.annotations.InsertProvider
 * @see org.apache.ibatis.annotations.DeleteProvider
 * @see org.apache.ibatis.annotations.UpdateProvider
 * @see org.apache.ibatis.annotations.SelectProvider
 * @since 1.0.0
 */
@FunctionalInterface
public interface SqlProvider extends ProviderMethodResolver {

    /**
     * return a script sql with provider context and mapper method parameters.
     *
     * @param context    provider context
     * @param parameters mapper method parameters
     * @return a script sql.
     */
    @NonNull
    String provide(@NonNull ProviderContext context, @NonNull Map<String, Object> parameters);

}
