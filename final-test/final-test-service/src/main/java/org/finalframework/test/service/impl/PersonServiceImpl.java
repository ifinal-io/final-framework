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

