/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.data.mybatis.sql.provider;

import org.ifinalframework.context.user.UserContextHolder;
import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IQuery;
import org.ifinalframework.data.annotation.AbsUser;
import org.ifinalframework.data.annotation.YN;
import org.ifinalframework.data.mybatis.mapper.AbsMapper;
import org.ifinalframework.data.mybatis.sql.util.SqlHelper;
import org.ifinalframework.data.query.Update;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class UpdateSqlProviderTest {

    /**
     * @throws NoSuchMethodException exception
     * @see UpdateSqlProvider#update(ProviderContext, Map)
     * @see AbsMapper#update(String, Class, IEntity, Update, boolean, Collection, IQuery)
     */
    @Test
    void update() throws NoSuchMethodException {


        AbsUser user = new AbsUser();
        user.setId(1L);
        user.setName("hahah");
        UserContextHolder.setUser(user);


        final HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("table", "person");
        parameters.put("view", null);
        parameters.put("selective", false);
        Person person = new Person();
        person.setAge(12);
        person.setName("haha");
        person.setYn(YN.NO);
        parameters.put("entity", person);

        //        parameters.put("update", Update.update().set("name", "haha"));

        final PersonQuery query = new PersonQuery();
        query.setLocation(new Point());
        query.setDistance(1L);
        parameters.put("query", query);


        logger.info(SqlHelper.xml(PersonMapper.class, "update", parameters));
        logger.info(SqlHelper.sql(PersonMapper.class, "update", parameters));
        UserContextHolder.reset();
    }

}
