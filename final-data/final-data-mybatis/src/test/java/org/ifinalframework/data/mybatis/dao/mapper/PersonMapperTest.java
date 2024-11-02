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

package org.ifinalframework.data.mybatis.dao.mapper;

import ch.qos.logback.classic.Level;

import org.springframework.boot.test.context.SpringBootTest;

import org.ifinalframework.context.user.UserContextHolder;
import org.ifinalframework.data.mybatis.entity.Person;
import org.ifinalframework.data.mybatis.entity.User;
import org.ifinalframework.data.mybatis.sql.util.SqlHelper;

import org.apache.ibatis.binding.MapperMethod;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;

import java.util.Arrays;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PersonMapperTest.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 */
@Slf4j
@SpringBootTest
class PersonMapperTest {

    @Resource
    private PersonMapper personMapper;

    @Test
    void insertAndUpdate() {


        Logger logger = LoggerFactory.getLogger(PersonMapper.class);
        ((ch.qos.logback.classic.Logger) logger).setLevel(Level.DEBUG);
        User user = new User();
        user.setId(1L);
        user.setName("123");
        UserContextHolder.setUser(user);

        personMapper.truncate();


        Person person = new Person();
        person.setName("haha");
        person.setAge(13);

        Map<String, Object> parameters = new MapperMethod.ParamMap<>();
        parameters.put("table", "person");
        parameters.put("view", null);
        parameters.put("query", null);
        parameters.put("selective", null);
        parameters.put("USER", null);
        parameters.put("list", Arrays.asList(person));
        //        parameters.put("table","person");
        PersonMapperTest.logger.info(SqlHelper.xml(PersonMapper.class, "insert", parameters));
        PersonMapperTest.logger.info(SqlHelper.sql(PersonMapper.class, "insert", parameters));

        personMapper.insert(person);

        person = personMapper.selectOne(person.getId());
        assertEquals("123", person.getCreator().getName());

        user.setName("234");
        UserContextHolder.setUser(user);

        person.setAge(16);
        parameters.put("entity", person);
        PersonMapperTest.logger.info(SqlHelper.xml(PersonMapper.class, "update", parameters));
        PersonMapperTest.logger.info(SqlHelper.sql(PersonMapper.class, "update", parameters));
        person.setLastModifier(null);
        person.setCreator(null);
        personMapper.update(person);
        person = personMapper.selectOne(person.getId());
        assertEquals("234", person.getLastModifier().getName());
        assertEquals(16, person.getAge());

        UserContextHolder.reset();

    }
}