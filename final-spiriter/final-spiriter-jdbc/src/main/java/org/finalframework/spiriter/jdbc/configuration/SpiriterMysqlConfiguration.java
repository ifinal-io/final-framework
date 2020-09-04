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

package org.finalframework.spiriter.jdbc.configuration;


import org.apache.ibatis.session.Configuration;
import org.finalframework.spiriter.jdbc.dao.mapper.ColumnsMapper;
import org.finalframework.spiriter.jdbc.dao.mapper.CommonMapper;
import org.finalframework.spiriter.jdbc.dao.mapper.TablesMapper;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-20 20:41:36
 * @since 1.0
 */
@SpringComponent
public class SpiriterMysqlConfiguration implements ConfigurationCustomizer {
    @Override
    public void customize(Configuration configuration) {
        configuration.addMapper(CommonMapper.class);
        configuration.addMapper(ColumnsMapper.class);
        configuration.addMapper(TablesMapper.class);
    }
}

