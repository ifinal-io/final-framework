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

import org.finalframework.monitor.annotation.MonitorAction;
import org.finalframework.test.entity.Person;
import org.finalframework.test.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-23 16:22:54
 * @since 1.0
 */
@RestController
@RequestMapping("/query")
public class QueryController {
    public static final Logger logger = LoggerFactory.getLogger(QueryController.class);

    @Resource
    private PersonService personService;

    @GetMapping
    @MonitorAction(name = "查询Person:{#id}:{#id + 'haha'}", target = "{#id}")
    public Object query(Long id) {
        System.out.println(personService.selectCount());
        return personService.findById(id);
    }

    @PostMapping
    public Person save(Person person) {
        int save = personService.save(person);
        logger.info("save={}", save);
        return person;
    }
}

