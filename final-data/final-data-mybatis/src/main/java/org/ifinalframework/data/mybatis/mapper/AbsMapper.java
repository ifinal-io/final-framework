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

package org.ifinalframework.data.mybatis.mapper;

import org.springframework.lang.NonNull;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.data.mybatis.sql.provider.DeleteSqlProvider;
import org.ifinalframework.data.mybatis.sql.provider.InsertSqlProvider;
import org.ifinalframework.data.mybatis.sql.provider.SelectCountSqlProvider;
import org.ifinalframework.data.mybatis.sql.provider.SelectSqlProvider;
import org.ifinalframework.data.mybatis.sql.provider.TruncateSqlProvider;
import org.ifinalframework.data.mybatis.sql.provider.UpdateSqlProvider;
import org.ifinalframework.data.repository.Repository;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author iimik
 * @version 1.0.0
 * @see InsertSqlProvider
 * @see UpdateSqlProvider
 * @see SelectSqlProvider
 * @see SelectCountSqlProvider
 * @see DeleteSqlProvider
 * @since 1.0.0
 */
@SuppressWarnings("all")
public interface AbsMapper<I extends Serializable, T extends IEntity<I>> extends Repository<I, T> {

    /**
     * Use {@link Options#useGeneratedKeys()} to get the auto increment key.
     *
     * @see InsertSqlProvider#insert(ProviderContext, Map)
     */
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "list.id", keyColumn = "id")
    @InsertProvider(InsertSqlProvider.class)
    int insert(@NonNull Map<String, Object> params);


    /**
     * @see InsertSqlProvider#replace(ProviderContext, Map)
     */
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "list.id", keyColumn = "id")
    @InsertProvider(InsertSqlProvider.class)
    int replace(@NonNull Map<String, Object> params);

    /**
     * @see InsertSqlProvider#save(ProviderContext, Map)
     */
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "list.id", keyColumn = "id")
    @InsertProvider(InsertSqlProvider.class)
    int save(@NonNull Map<String, Object> params);

    @Override
    @UpdateProvider(UpdateSqlProvider.class)
    int update(@NonNull Map<String, Object> params);

    @Override
    @DeleteProvider(DeleteSqlProvider.class)
    int delete(@NonNull Map<String, Object> params);

    @Override
    @SelectProvider(SelectSqlProvider.class)
    List<T> select(@NonNull Map<String, Object> params);

    @Override
    @SelectProvider(SelectSqlProvider.class)
    T selectOne(@NonNull Map<String, Object> params);

    @Override
    @SelectProvider(SelectSqlProvider.class)
    List<I> selectIds(@NonNull Map<String, Object> params);

    @Override
    @SelectProvider(SelectCountSqlProvider.class)
    long selectCount(@NonNull Map<String, Object> params);

    @Override
    @UpdateProvider(TruncateSqlProvider.class)
    void truncate(@NonNull Map<String, Object> params);

}
