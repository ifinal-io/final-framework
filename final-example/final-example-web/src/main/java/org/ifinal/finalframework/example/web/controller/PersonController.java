package org.ifinal.finalframework.example.web.controller;

import java.util.List;
import javax.annotation.Resource;
import org.ifinal.finalframework.example.dao.query.PersonQuery;
import org.ifinal.finalframework.example.entity.Person;
import org.ifinal.finalframework.example.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Resource
    private PersonService personService;

    @GetMapping
    public List<Person> query(final PersonQuery query) {

        return personService.select(query);
    }

}
