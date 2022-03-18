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

package org.ifinalframework.spiriter.jdbc.converter;

import org.ifinalframework.spiriter.jdbc.service.DataSourceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ifinalframework.core.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * @author ilikly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
 @Component
@Component
public class String2DataSourceConverter implements Converter<String, DataSource> {

    public static final Logger logger = LoggerFactory.getLogger(String2DataSourceConverter.class);

    @Resource
    private DataSourceService dataSourceService;

    @Override
    public DataSource convert(String source) {
        return dataSourceService.getDataSource(source);
    }
}
