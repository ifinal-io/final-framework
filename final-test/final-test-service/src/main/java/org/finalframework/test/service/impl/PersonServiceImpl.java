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

package org.finalframework.test.service.impl;

import org.finalframework.data.service.AbsServiceImpl;
import org.finalframework.test.dao.mapper.PersonMapper;
import org.finalframework.test.entity.Person;
import org.finalframework.test.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;


/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 13:58:37
 * @since 1.0
 */
@Service
public class PersonServiceImpl extends AbsServiceImpl<Long, Person, PersonMapper> implements PersonService {
    public static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);


    public PersonServiceImpl(ObjectProvider<PersonMapper> repositoryProvider) {
        super(repositoryProvider);
    }
}

