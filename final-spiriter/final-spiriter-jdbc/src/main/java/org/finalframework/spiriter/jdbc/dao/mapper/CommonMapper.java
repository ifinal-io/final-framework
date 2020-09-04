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

package org.finalframework.spiriter.jdbc.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-20 22:49:45
 * @since 1.0
 */
@Mapper
public interface CommonMapper {

    @Select("show databases")
    List<String> showDatabases();

    @Select("select database()")
    String selectDatabase();

    @Select("select version()")
    String selectVersion();

    @Select("show tables")
    List<String> showTables();


}
