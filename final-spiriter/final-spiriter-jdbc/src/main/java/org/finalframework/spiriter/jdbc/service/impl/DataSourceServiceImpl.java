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

package org.finalframework.spiriter.jdbc.service.impl;

import org.finalframework.spiriter.jdbc.service.DataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 12:37:39
 * @since 1.0
 */
@Service
public class DataSourceServiceImpl implements DataSourceService, ApplicationContextAware {
    public static final Logger logger = LoggerFactory.getLogger(DataSourceServiceImpl.class);

    private Map<String, DataSource> dataSourceMap;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.dataSourceMap = applicationContext.getBeansOfType(DataSource.class);
    }

    @Override
    public Map<String, DataSource> getDataSources() {
        return dataSourceMap;
    }
}

