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

package org.finalframework.test.controller;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;

import org.finalframework.coding.query.QProperty;
import org.finalframework.data.annotation.IView;
import org.finalframework.data.query.PageQuery;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Update;
import org.finalframework.test.dao.query.QPerson;
import org.finalframework.test.entity.Person;
import org.finalframework.test.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author likly
 * @version 1.0
 * @date 2020-03-18 13:07:50
 * @since 1.0
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    public static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Resource
    private PersonService personService;

    @GetMapping
    public List<Person> query(PageQuery query) {

//        return Arrays.asList(personService.selectOne1());
//        final long count = personService.selectCount(1L);
        return personService.select(new Query()
                        .where(
//                        QPerson.age.eq(101),
//                        QPerson.created.date().gt("2020-01-01 00:00:00"),
//                        QPerson.name.eq("haha"),
//                        QPerson.intList.jsonExtract("$[1]").eq(2),t
//                                QPerson.properties.jsonContains("[1,2]", "$.int"),
                                QPerson.properties.jsonContains("\"aaa\"", "$.string")
                        )
        );
//        return personService.select(new Query().page(query.getPage(), query.getSize()));
    }

    @PostMapping
    public Person insert(Person person, @RequestParam(required = false, defaultValue = "false") boolean ignore,
                         @RequestParam(value = "view", required = false) Class<?> view) {
        int save = personService.insert(view, ignore, person);
        logger.info("save={}", save);
        return person;
    }


    @PostMapping("/save")
    public Person save(Person person) {
        int save = personService.save(person);
        logger.info("save={}", save);
        return person;
    }

    @PostMapping("/update")
    public int update(Person person) {

        final Update update = Update.update()
                .set(QPerson.name, person.getName())
                .set(QPerson.intList, Arrays.asList(1, 23, 4, 56));

        final int rows = personService.update(update, person.getId());


        return personService.update(person);
    }
}

