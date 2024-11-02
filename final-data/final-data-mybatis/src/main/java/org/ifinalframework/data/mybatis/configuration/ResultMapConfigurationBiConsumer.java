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

package org.ifinalframework.data.mybatis.configuration;

import org.springframework.util.ReflectionUtils;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.data.mybatis.mapping.DefaultResultMapFactory;
import org.ifinalframework.data.mybatis.mapping.ResultMapFactory;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * ResultConfigurationBiConsumer.
 *
 * @author iimik
 * @version 1.4.0
 * @see org.ifinalframework.data.mybatis.builder.MapperBuilderAssistantExt
 * @since 1.4.0
 * @deprecated replaced by {@link org.ifinalframework.data.mybatis.builder.MapperBuilderAssistantExt} since 1.5.2
 */
@Slf4j
@Deprecated
public class ResultMapConfigurationBiConsumer implements ConfigurationBiConsumer {

    private static final Field composites = Objects
            .requireNonNull(ReflectionUtils.findField(ResultMapping.class, "composites"));

    private final ResultMapFactory resultMapFactory = new DefaultResultMapFactory();

    static {
        ReflectionUtils.makeAccessible(composites);
    }

    @Override
    public void accept(Configuration configuration, Class<? extends IEntity<?>> clazz) {
        ResultMap resultMap = resultMapFactory.create(configuration, clazz);

        if (logger.isInfoEnabled()) {
            logger.info("==> addResultMap:[{}],class={}", resultMap.getId(), clazz);
        }

        configuration.addResultMap(resultMap);

        resultMap.getResultMappings()
                .stream()
                .filter(ResultMapping::isCompositeResult)
                .forEach(resultMapping -> {

                    ResultMap map = new ResultMap.Builder(configuration, resultMapping.getNestedResultMapId(),
                            resultMapping.getJavaType(),
                            resultMapping.getComposites()).build();
                    configuration.addResultMap(map);

                    // mybatis not support composites result mapping
                    ReflectionUtils.setField(composites, resultMapping, null);

                });
    }
}


