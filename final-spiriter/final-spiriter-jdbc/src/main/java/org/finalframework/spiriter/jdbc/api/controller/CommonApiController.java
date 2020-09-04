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

package org.finalframework.spiriter.jdbc.api.controller;

import java.util.List;
import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.finalframework.spiriter.jdbc.dao.mapper.CommonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-20 20:44:42
 * @since 1.0
 */
@RestController
@RequestMapping("/api/jdbc")
public class CommonApiController implements InitializingBean {

    public static final Logger logger = LoggerFactory.getLogger(CommonApiController.class);

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    private CommonMapper commonMapper;

    @GetMapping("/databases")
    public List<String> databases() {
        return commonMapper.showDatabases();
    }

    @GetMapping("/database")
    public String database() {
        return commonMapper.selectDatabase();
    }

    @GetMapping("/version")
    public String version() {
        return commonMapper.selectVersion();
    }

    @GetMapping("/showTables")
    public List<String> showTables() {
        return commonMapper.showTables();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.commonMapper = sqlSessionFactory.getConfiguration()
                .getMapper(CommonMapper.class, sqlSessionFactory.openSession());

    }
}

