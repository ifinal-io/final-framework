package org.finalframework.test.controller;

import org.finalframework.test.dao.mapper.PersonMapper;
import org.finalframework.test.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


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
    private PersonMapper personMapper;

    @PostMapping
    public Person insert(Person person, @RequestParam(required = false, defaultValue = "false") boolean ignore) {
        int save = personMapper.insert(ignore, person);
        logger.info("save={}", save);
        return person;
    }

    @PostMapping("/save")
    public Person save(Person person) {
        int save = personMapper.save(person);
        logger.info("save={}", save);
        return person;
    }
}

