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

import java.io.IOException;

import org.finalframework.test.entity.Person;
import org.finalframework.test.service.PersonRetrofit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author likly
 * @version 1.0
 * @date 2020-04-25 17:43:01
 * @since 1.0
 */
@RestController
@RequestMapping("/retrofit/person")
public class PersonRetrofitController {
    public static final Logger logger = LoggerFactory.getLogger(PersonRetrofitController.class);

    //    @Resource
    private PersonRetrofit personRetrofit;

    @GetMapping
    public Person findById(Long id) throws IOException {
        return personRetrofit.findById(id).execute().body().getData();
    }
}

