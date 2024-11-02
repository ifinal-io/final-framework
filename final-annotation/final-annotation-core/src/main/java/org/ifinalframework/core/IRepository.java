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

package org.ifinalframework.core;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * IRepository.
 *
 * @author iimik
 * @version 1.2.2
 * @see ParamsBuilder
 * @since 1.2.2
 */
public interface IRepository<I extends Serializable, T extends IEntity<I>> {

    /**
     * <pre class="code">
     * INSERT [IGNORE] INTO ${table} col1,col2,... VALUES (val1,val2,...)
     * </pre>
     *
     * @param params insert params
     * @return insert rows.
     * @see #replace(Map)
     * @see #save(Map)
     */
    int insert(@NonNull Map<String, Object> params);

    /**
     * <pre class="code">
     * REPLACE INTO ${table} col1,col2,... VALUES (val1,val2,...)
     * </pre>
     *
     * @param params replace params.
     * @return replace rows.
     * @see #insert(Map)
     * @see #save(Map)
     */
    int replace(@NonNull Map<String, Object> params);

    /**
     * <pre class="code">
     * INSERT INTO ${table} col1,col2,... VALUES (val1,val2,...) ON DUPLICATE KEY UPDATE col1=values(col1),col2=values(col2),...
     * </pre>
     *
     * @param params save params.
     * @return save rows.
     * @see #insert(Map)
     * @see #replace(Map)
     */
    int save(@NonNull Map<String, Object> params);

    /**
     * <pre class="code">
     * UPDATE ${table} SET col1=val1, col2=val2,... WHERE ...
     * </pre>
     */
    int update(@NonNull Map<String, Object> params);

    /**
     * <pre class="code">
     * DELETE ${table} WHERE ...
     * </pre>
     */
    int delete(@NonNull Map<String, Object> params);

    /**
     * <pre class="code">
     * SELECT col1,col2,... FROM ${table} WHERE ...
     * </pre>
     */
    @Nullable
    List<T> select(@NonNull Map<String, Object> params);

    /**
     * <pre class="code">
     * SELECT id FROM ${table} WHERE...
     * </pre>
     */
    @Nullable
    List<I> selectIds(@NonNull Map<String, Object> params);

    /**
     * <pre class="code">
     * SELECT col1,col2,... FROM ${table} WHERE...
     * </pre>
     */
    @Nullable
    T selectOne(@NonNull Map<String, Object> params);

    /**
     * <pre class="code">
     * SELECT COUNT(*) FROM ${table} WHERE...
     * </pre>
     */
    long selectCount(@NonNull Map<String, Object> params);

    /**
     * <pre class="code">
     * TRUNCATE ${table}
     * </pre>
     */
    void truncate(@NonNull Map<String, Object> params);


}
