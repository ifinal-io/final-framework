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

package org.ifinalframework.data.mybatis.builder;

import org.springframework.util.CollectionUtils;

import org.ifinalframework.data.mybatis.mapping.DefaultResultMapFactory;
import org.ifinalframework.data.mybatis.mapping.ResultMapFactory;
import org.ifinalframework.util.Primaries;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.Discriminator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.List;
import java.util.Objects;

/**
 * MapperBuilderAssistantExt.
 *
 * @author iimik
 * @version 1.5.1
 * @since 1.5.1
 */
public class MapperBuilderAssistantExt extends MapperBuilderAssistant {
    private static final ResultMapFactory resultMapFactory = new DefaultResultMapFactory();

    public MapperBuilderAssistantExt(Configuration configuration, String resource) {
        super(configuration, resource);
    }

    @Override
    public ResultMap addResultMap(String id, Class<?> type, String extend,
                                  Discriminator discriminator, List<ResultMapping> resultMappings, Boolean autoMapping) {

        if (CollectionUtils.isEmpty(resultMappings)) {
            final ResultMap resultMap = resultMapFactory.create(getConfiguration(), type);
            resultMappings = resultMap.getResultMappings();
        }

        if (Objects.isNull(autoMapping)) {
            autoMapping = true;
        }

        return super.addResultMap(id, type, extend, discriminator, resultMappings, autoMapping);
    }


    public MappedStatement addMappedStatement(String id, SqlSource sqlSource, StatementType statementType,
                                              SqlCommandType sqlCommandType, Integer fetchSize, Integer timeout,
                                              String parameterMap, Class<?> parameterType,
                                              String resultMap, Class<?> resultType, ResultSetType resultSetType,
                                              boolean flushCache, boolean useCache,
                                              boolean resultOrdered, KeyGenerator keyGenerator,
                                              String keyProperty, String keyColumn, String databaseId,
                                              LanguageDriver lang, String resultSets, boolean dirtySelect) {

        if (resultMap == null && resultType != null && resultType != void.class && !Primaries.isPrimary(resultType)) {

            final String statementId = applyCurrentNamespace(id, false);
            resultMap = statementId + "-Inline";

            ResultMap inlineResultMap = new ResultMap.Builder(configuration, resultMap, resultType,
                    resultMapFactory.create(getConfiguration(), resultType).getResultMappings(), true).build();

            configuration.addResultMap(inlineResultMap);

        }

        return super.addMappedStatement(id, sqlSource, statementType, sqlCommandType, fetchSize, timeout,
                parameterMap, parameterType, resultMap, resultType, resultSetType, flushCache, useCache,
                resultOrdered, keyGenerator, keyProperty, keyColumn, databaseId, lang, resultSets, dirtySelect);

    }
}
