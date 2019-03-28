package org.finalframework.test.service;

import org.finalframework.monitor.action.annotation.OperationAction;
import org.finalframework.test.dao.mapper.PersonMapper;
import org.finalframework.test.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 17:47:33
 * @since 1.0
 */
@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
    @Resource
    private PersonMapper personMapper;

    @Override
    @OperationAction(name = "PersonServiceImpl", target = "{#id}")
    public Person findById(Long id) {
        return personMapper.selectOne(id);
    }
}
