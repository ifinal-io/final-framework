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

package org.ifinalframework.spiriter.jdbc.configuration;


import org.apache.ibatis.session.Configuration;

import org.ifinalframework.spiriter.jdbc.dao.mapper.ColumnsMapper;
import org.ifinalframework.spiriter.jdbc.dao.mapper.CommonMapper;
import org.ifinalframework.spiriter.jdbc.dao.mapper.TablesMapper;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class SpiriterMysqlConfiguration implements ConfigurationCustomizer {
    @Override
    public void customize(Configuration configuration) {
        configuration.addMapper(CommonMapper.class);
        configuration.addMapper(ColumnsMapper.class);
        configuration.addMapper(TablesMapper.class);
    }
}

