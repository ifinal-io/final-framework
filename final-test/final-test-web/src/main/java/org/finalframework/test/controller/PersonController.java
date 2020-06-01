package org.finalframework.test.controller;

import org.finalframework.data.query.PageQuery;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.criterion.CriterionValue;
import org.finalframework.data.query.operation.JsonOperation;
import org.finalframework.test.dao.query.QPerson;
import org.finalframework.test.entity.Person;
import org.finalframework.test.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


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
        return personService.select(new Query().where(
                QPerson.age.eq(101),
                QPerson.intList.jsonContains(
                        CriterionValue.from(1).apply(JsonOperation.array()),
                        "$"
                )));
//        return personService.select(new Query().page(query.getPage(), query.getSize()));
    }

    @PostMapping
    public Person insert(Person person, @RequestParam(required = false, defaultValue = "false") boolean ignore) {
        int save = personService.insert(ignore, person);
        logger.info("save={}", save);
        return person;
    }


    @PostMapping("/save")
    public Person save(Person person) {
        int save = personService.save(person);
        logger.info("save={}", save);
        return person;
    }
}

