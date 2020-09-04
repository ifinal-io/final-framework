/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.spiriter.jdbc.converter;

import org.finalframework.spiriter.jdbc.service.DataSourceService;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.finalframework.core.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 12:57:05
 * @since 1.0
 */
@SpringComponent
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
