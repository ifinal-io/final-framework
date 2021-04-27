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

package org.ifinalframework.spiriter.jdbc.api.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.ifinalframework.data.query.Query;
import org.ifinalframework.spiriter.jdbc.dao.mapper.ColumnsMapper;
import org.ifinalframework.spiriter.jdbc.dao.query.QColumns;
import org.ifinalframework.spiriter.jdbc.entity.Columns;
import org.ifinalframework.spiriter.jdbc.query.ColumnsQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author likly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/jdbc")
public class ColumnsApiController implements InitializingBean {
    public static final Logger logger = LoggerFactory.getLogger(ColumnsApiController.class);

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    private ColumnsMapper columnsMapper;

    @GetMapping("/columns")
    public List<Columns> columns(ColumnsQuery query) {
        return columnsMapper.select(new Query().where(
                QColumns.schema.eq(query.getSchema()),
                QColumns.table.eq(query.getTable())
        ));
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.columnsMapper = sqlSessionFactory.getConfiguration().getMapper(ColumnsMapper.class, sqlSessionFactory.openSession());

    }
}

